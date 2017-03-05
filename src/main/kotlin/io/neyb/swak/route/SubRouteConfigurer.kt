package io.neyb.swak.route

import io.neyb.swak.route.RequestHandler
import io.neyb.swak.route.interceptors.before.PathParamExtractor
import io.neyb.swak.route.matcher.MethodMatcher
import io.neyb.swak.route.matcher.PathMatcher
import io.neyb.swak.route.path.RoutePath
import io.neyb.swak.http.*
import io.neyb.swak.route.configuration.RouteConfiguration
import io.neyb.swak.route.interceptors.before.reader.BeforeRouteInterceptor
import io.neyb.swak.route.interceptors.before.reader.RequestContentReaderProvider
import io.neyb.swak.route.interceptors.errorHandler.ErrorHandler
import io.reactivex.Single

class SubRouteConfigurer<Body, InnerBody>(
        private val subRouteBuilder: SubRouteBuilder<Body, InnerBody>,
        private val innerbodyClass: Class<InnerBody>
) {

    fun apply(routeConfiguration: RouteConfiguration<Body, InnerBody>) {
        routeConfiguration(this)
    }

    inline fun <reified E : Throwable> addErrorHandler(noinline errorHandler: (E) -> Response) {
        addErrorHandler(ErrorHandler.of(errorHandler))
    }

    fun addErrorHandler(errorHandler: ErrorHandler) {
        subRouteBuilder.addErrorhandler(errorHandler)
    }

    fun addContentReaderProvider(contentReaderProvider: RequestContentReaderProvider) {
        subRouteBuilder.addContentReaderProvider(contentReaderProvider)
    }

    fun handle(method: Method, path: String, handler: (Request<String>) -> Single<Response>) {
        handleTyped(method, path, handler)
    }

    inline fun <reified B> handleTyped(method: Method, path: String, noinline handler: (Request<B>) -> Single<Response>) {
        handleTyped(method, path, B::class.java, handler)
    }

    fun <B> handleTyped(method: Method, path: String, bodyType: Class<B>, handler: (Request<B>) -> Single<Response>) {
        handle(method, path, bodyType, handler.asRequestHandler())
    }

    private fun <B> handle(method: Method, path: String, bodyType: Class<B>, requestHandler: RequestHandler<B>) {
        val routePath = RoutePath.of(path)

        val newSubRouteBuilder = SubRouteBuilder(
                subRouteBuilder.pathPrefix,
                subRouteBuilder.innerBody,
                bodyType,
                subRouteBuilder.contentReaderProviders
        ).apply {
            requestMatcher = MethodMatcher<InnerBody>(method) and PathMatcher(routePath)
            beforeRouteInterceptor = BeforeRouteInterceptor(
                    preContentNegociationInterceptor = PathParamExtractor<InnerBody>(routePath),
                    requestContentReaders = contentReaderProviders.forClass(innerbodyClass, bodyType)
                            ?: throw NoReaderFound()
            )
            routes.add(FinalRoute(requestHandler))
        }


        subRouteBuilder.routes.add(newSubRouteBuilder.build())
    }

    private fun <B> ((Request<B>) -> Single<Response>).asRequestHandler(): RequestHandler<B> =
            object : RequestHandler<B> {
                override fun handle(request: Request<B>) = this@asRequestHandler(request)
            }
}
