package io.neyb.swak.handler.cross

import io.neyb.swak.handler.Handler
import io.neyb.swak.handler.HandlerBuilder
import io.neyb.swak.handler.cross.route.Route
import io.neyb.swak.http.Request
import io.neyb.swak.http.Response
import io.reactivex.Single
import java.util.*

class Cross<Body>(
        private val routes: List<Route<Body>>
) : Handler<Body> {

    override fun handle(request: Request<Body>): Single<Response> {
        val acceptingRoutes = routes.filter { it.accept(request) }
        return when (acceptingRoutes.size) {
            0 -> Single.error { NoRouteFound(request.path) }
            1 -> acceptingRoutes.single().handle(request)
            else -> Single.error { SeveralRouteFound(request.path, acceptingRoutes) }
        }
    }

    class Builder : HandlerBuilder<String> {
        val routes: MutableList<Route<String>> = ArrayList()
        override fun build() = Cross(routes)
    }
}

