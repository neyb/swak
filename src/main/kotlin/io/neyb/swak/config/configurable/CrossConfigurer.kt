package io.neyb.swak.config.configurable

import io.neyb.swak.config.builder.ConfigurableCrossHandler
import io.neyb.swak.handler.Handler
import io.neyb.swak.handler.converter.BodyConverterHandler
import io.neyb.swak.handler.converter.reader.provider.type.BodyReaderTypeProvider
import io.neyb.swak.handler.cross.route.Route
import io.neyb.swak.handler.cross.route.matcher.MethodMatcher
import io.neyb.swak.handler.cross.route.matcher.PathMatcher
import io.neyb.swak.handler.interceptor.InterceptableHandler
import io.neyb.swak.handler.interceptor.before.PathParamExtractor
import io.neyb.swak.handler.path.RoutePath
import io.neyb.swak.http.*
import io.reactivex.Single

class CrossConfigurer(
        private val configurableCrossHandler: ConfigurableCrossHandler
) : HandlerConfigurer(configurableCrossHandler) {

    fun addContentReaderProvider(bodyReaderTypeProvider: BodyReaderTypeProvider) {
        configurableCrossHandler.bodyReaderTypeProviders.add(bodyReaderTypeProvider)
    }

    inline fun <reified B> handleTyped(method: Method, path: String, noinline handler: (Request<B>) -> Single<Response>) {
        handleTyped(method, path, B::class.java, handler)
    }

    fun <B> handleTyped(method: Method, path: String, bodyType: Class<B>, handler: (Request<B>) -> Single<Response>) {
        handleTyped(method, path, bodyType, handler.asRequestHandler())
    }

    private fun <B> handleTyped(method: Method, path: String, bodyType: Class<B>, handler: Handler<B>) {
        handle(method, path, BodyConverterHandler(
                configurableCrossHandler.bodyReaderTypeProviders.forClass(bodyType),
                handler))
    }

    fun handle(method: Method, path: String, handler: (Request<String>) -> Single<Response>) {
        handle(method, path, handler.asRequestHandler())
    }

    private fun handle(method: Method, path: String, handler: Handler<String>) {
        val routePath = RoutePath.of(path)

        configurableCrossHandler.addRoute(Route(
                MethodMatcher<String>(method) and PathMatcher(routePath),
                InterceptableHandler.Builder<String>().apply {
                    before.interceptors.add(PathParamExtractor(routePath))
                }.build(handler)
        ))
    }

    private fun <B> ((Request<B>) -> Single<Response>).asRequestHandler(): Handler<B> =
            object : Handler<B> {
                override fun handle(request: Request<B>) = this@asRequestHandler(request)
            }

}