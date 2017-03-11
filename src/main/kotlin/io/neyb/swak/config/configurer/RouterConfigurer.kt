package io.neyb.swak.config.configurer

import io.neyb.swak.handler.cross.Router
import io.neyb.swak.handler.cross.route.Route
import io.neyb.swak.http.*
import io.reactivex.Single

interface RouterConfigurer : HandlerConfigurer {
    val routerHandlerBuilder: Router.Builder
    fun addRoute(route: Route)
    fun handle(path: String, method: Method, handler: (Request<String>) -> Single<Response>)
    fun <B> handleTyped(path: String, method: Method, bodyType: Class<B>, handler: (Request<B>) -> Single<Response>)
    fun sub(path: String, configuration: SubRouteConfigurer.() -> Unit)
}