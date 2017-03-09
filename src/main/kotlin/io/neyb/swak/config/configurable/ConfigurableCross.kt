package io.neyb.swak.config.configurable

import io.neyb.swak.config.configurer.CrossConfigurer
import io.neyb.swak.handler.Handler
import io.neyb.swak.handler.converter.BodyConverterHandler
import io.neyb.swak.handler.cross.Cross
import io.neyb.swak.handler.cross.route.Route
import io.neyb.swak.handler.cross.route.matcher.MethodMatcher
import io.neyb.swak.handler.cross.route.matcher.PathMatcher
import io.neyb.swak.handler.interceptor.InterceptableHandler
import io.neyb.swak.handler.interceptor.before.PathParamUpdater
import io.neyb.swak.handler.path.RoutePath
import io.neyb.swak.http.*
import io.reactivex.Single

interface ConfigurableCross : CrossConfigurer, ConfigurableHandler<String> {
    override val crossHandlerBuilder: Cross.Builder

    override fun addRoute(route: Route) {
        crossHandlerBuilder.routes.add(route)
    }

    override fun <B> handleTyped(method: Method, path: String, bodyType: Class<B>, handler: (Request<B>) -> Single<Response>) {
        handleTyped(method, path, bodyType, handler.asRequestHandler())
    }

    private fun <B> handleTyped(method: Method, path: String, bodyType: Class<B>, handler: Handler<B>) {
        handle(method, path, BodyConverterHandler(
                bodyReaderTypeProviders.forClass(bodyType),
                handler))
    }

    override fun handle(method: Method, path: String, handler: (Request<String>) -> Single<Response>) {
        handle(method, path, handler.asRequestHandler())
    }

    private fun handle(method: Method, path: String, handler: Handler<String>) {
        val routePath = RoutePath.of(this.path + path)

        addRoute(Route(
                MethodMatcher<String>(method) and PathMatcher(routePath),
                InterceptableHandler.Builder<String>().apply {
                    before.interceptors.add(PathParamUpdater(routePath))
                }.build(handler)
        ))
    }

    override fun build(): Handler<String> = crossHandlerBuilder.build()
}