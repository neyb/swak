package swak.config.configurable

import io.reactivex.Single
import swak.config.configurer.RouterConfigurer
import swak.config.configurer.SubRouteConfigurer
import swak.handler.Handler
import swak.handler.converter.BodyConverterHandler
import swak.handler.cross.Router
import swak.handler.cross.route.Route
import swak.handler.cross.route.matcher.*
import swak.handler.interceptor.Around
import swak.handler.interceptor.before.PathParamUpdater
import swak.handler.path.RoutePath
import swak.http.*

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