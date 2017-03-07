package io.neyb.swak.config.configurer

import io.neyb.swak.handler.cross.Cross
import io.neyb.swak.handler.cross.route.Route
import io.neyb.swak.http.*
import io.reactivex.Single

interface CrossConfigurer : HandlerConfigurer {
    val crossHandlerBuilder: Cross.Builder
    fun addRoute(route: Route)
    fun handle(method: Method, path: String, handler: (Request<String>) -> Single<Response>)
    fun <B> handleTyped(method: Method, path: String, bodyType: Class<B>, handler: (Request<B>) -> Single<Response>)
}