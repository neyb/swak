package swak.config.configurable

import io.reactivex.Single
import swak.config.configurer.*
import swak.handler.*
import swak.handler.NotWritable.NotWritableHandler
import swak.handler.path.RoutePath
import swak.handler.router.Router
import swak.handler.router.route.Route
import swak.http.request.Method
import swak.http.request.context.RequestContext
import swak.http.response.NotWritableResponse
import swak.interceptor.before.PathParamUpdater
import swak.matcher.*

internal interface ConfigurableRouter : RouterConfigurer, ConfigurableHandler<String> {
    val routerHandlerBuilder: Router.Builder

    fun addRoute(route: Route) {
        routerHandlerBuilder.routes.add(route)
    }

    override fun <IB, OB : Any> handle(
            path: String,
            method: Method,
            reqBodyType: Class<IB>,
            respBodyType: Class<OB>,
            handler: RequestContext<IB>.() -> Single<out NotWritableResponse<OB>>) {
        handleTyped(path, method, reqBodyType, respBodyType, handler.asRequestHandler(), haveSubRoute = false)
    }

    private fun <IB, OB : Any> handleTyped(
            path: String,
            method: Method,
            reqBodyType: Class<IB>,
            respBodyType: Class<OB>,
            handler: NotWritableHandler<IB, OB>,
            haveSubRoute: Boolean) {
        handle(path,
                method,
                BodyConverterHandler(
                        bodyReaderTypeProviders.forClass(reqBodyType),
                        handler,
                        bodyWriterTypeProviders.forClass(respBodyType)),
                haveSubRoute)
    }

    override fun sub(path: String, configuration: SubRouteConfigurer.() -> Unit) {
        val subRouteConfigurable = SubRouteConfigurable(this, path)
        SubRouteConfigurer(subRouteConfigurable).apply(configuration)
        handle(path, null, subRouteConfigurable.build(), haveSubRoute = true)
    }

    private fun handle(path: String, method: Method, handler: Handler<String>, haveSubRoute: Boolean) {
        handle(path, MethodMatcher(method), handler, haveSubRoute)
    }

    private fun handle(path: String, additionalMatcher: RequestMatcher<String>?, handler: Handler<String>, haveSubRoute: Boolean) {
        val routePath = RoutePath.of(this.path + path, !haveSubRoute)
        val matcher = PathMatcher<String>(routePath) and additionalMatcher

        val handlerWithAnyParamUpdater = Around.Builder<String>().apply {
            innerHandler = AlreadyBuiltHandlerBuilder(handler)
            if (routePath.extractor != null)
                before.interceptors.add(PathParamUpdater(routePath))
            //TODO this is ugly : around and exceptionHandler don't have same requirement => they should be split
        }.build(bodyWriterTypeProviders)

        addRoute(Route(matcher, handlerWithAnyParamUpdater))
    }

    infix fun RequestMatcher<String>.and(additionalMatcher: RequestMatcher<String>?) =
            if (additionalMatcher != null) this and additionalMatcher
            else this

    override fun build(): Handler<String> = routerHandlerBuilder.build()

}