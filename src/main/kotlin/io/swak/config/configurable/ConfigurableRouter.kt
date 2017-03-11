package io.swak.config.configurable

import io.swak.config.configurer.RouterConfigurer
import io.swak.config.configurer.SubRouteConfigurer
import io.swak.handler.Handler
import io.swak.handler.converter.BodyConverterHandler
import io.swak.handler.cross.Router
import io.swak.handler.cross.route.Route
import io.swak.handler.cross.route.matcher.*
import io.swak.handler.interceptor.Around
import io.swak.handler.interceptor.before.PathParamUpdater
import io.swak.handler.path.RoutePath
import io.swak.http.*
import io.reactivex.Single
import io.swak.handler.cross.route.matcher.*
import io.swak.http.Method
import io.swak.http.Request

internal interface ConfigurableRouter : RouterConfigurer, ConfigurableHandler<String> {
    val routerHandlerBuilder: Router.Builder

    fun addRoute(route: Route) {
        routerHandlerBuilder.routes.add(route)
    }

    override fun handle(path: String, method: Method, handler: (Request<String>) -> Single<Response>) {
        handle(path, method, handler.asRequestHandler(), haveSubRoute = false)
    }

    override fun <B> handleTyped(path: String, method: Method, bodyType: Class<B>, handler: (Request<B>) -> Single<Response>) {
        handleTyped(path, method, bodyType, handler.asRequestHandler(), haveSubRoute = false)
    }

    override fun sub(path: String, configuration: SubRouteConfigurer.() -> Unit) {
        val subRouteConfigurable = SubRouteConfigurable(this, path)
        SubRouteConfigurer(subRouteConfigurable).apply(configuration)
        handle(path, null, subRouteConfigurable.build(), haveSubRoute = true)
    }

    private fun <B> handleTyped(path: String, method: Method, bodyType: Class<B>, handler: Handler<B>, haveSubRoute: Boolean) {
        handle(path, method, BodyConverterHandler(
                bodyReaderTypeProviders.forClass(bodyType),
                handler),
                haveSubRoute)
    }

    private fun handle(path: String, method: Method, handler: Handler<String>, haveSubRoute: Boolean) {
        handle(path, MethodMatcher(method), handler, haveSubRoute)
    }

    private fun handle(path: String, additionalMatcher: RequestMatcher<String>?, handler: Handler<String>, haveSubRoute: Boolean) {
        val routePath = RoutePath.of(this.path + path, !haveSubRoute)
        val matcher = PathMatcher<String>(routePath) and additionalMatcher
        val handlerWithParamUpdater = Around.Builder<String>().apply {
            before.interceptors.add(PathParamUpdater(routePath))
        }.build(handler)

        addRoute(Route(matcher, handlerWithParamUpdater))
    }

    infix fun RequestMatcher<String>.and(additionalMatcher: RequestMatcher<String>?) =
            if (additionalMatcher != null) this and additionalMatcher
            else this

    override fun build(): Handler<String> = routerHandlerBuilder.build()

}