package io.neyb.swak.chain

import io.neyb.swak.chain.interceptor.before.BeforeInterceptor
import io.neyb.swak.chain.route.Route
import io.neyb.swak.chain.route.interceptors.PathParamExtractor
import io.neyb.swak.chain.route.interceptors.body.reader.*
import io.neyb.swak.chain.route.matcher.MethodMatcher
import io.neyb.swak.chain.route.matcher.PathMatcher
import io.neyb.swak.chain.route.path.RoutePath
import io.neyb.swak.http.*
import io.reactivex.Single

class ChainConfigurer(val chain: Chain) {

    private val contentReaderProviders = mutableListOf<RequestContentReaderProvider>()

    fun addContentReaderProvider(contentReaderProvider: RequestContentReaderProvider) {
        contentReaderProviders.add(contentReaderProvider)
    }

    fun handle(method: Method, path: String, handler: (Request<String?>) -> Single<Response>) {
        handleTyped(method, path, handler)
    }

    inline fun <reified B> handleTyped(method: Method, path: String, noinline handler: (Request<B>) -> Single<Response>) {
        handleTyped(method, path, B::class.java, handler)
    }

    fun <B> handleTyped(method: Method, path: String, bodyType: Class<B>, handler: (Request<B>) -> Single<Response>) {
        handle(method, path, bodyType, handler.asRequestHandler())
    }

    fun <B> handle(method: Method, path: String, bodyType: Class<B>, handler: RequestHandler<B>) {
        val routePath = RoutePath.of(path)
        chain.routes.add(Route(
                MethodMatcher(method) and PathMatcher(routePath),
                BeforeRouteInterceptor(
                        preContentNegociationInterceptor = PathParamExtractor(routePath),
                        requestContentReaders = RequestContentReaders(contentReaderProviders
                                .mapNotNull { it.forClass(bodyType) }),
                        postContentNegociationInterceptor = object : BeforeInterceptor<B, B> {
                            override fun updateRequest(request: Request<B>): Single<Request<B>> {
                                return Single.just(request)
                            }
                        }
                ),
                handler))
    }

    private fun <B> ((Request<B>) -> Single<Response>).asRequestHandler(): RequestHandler<B> =
            object : RequestHandler<B> {
                override fun handle(request: Request<B>) = this@asRequestHandler(request)
            }
}

