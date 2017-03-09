package io.neyb.swak.handler.cross

import io.neyb.swak.handler.Handler
import io.neyb.swak.handler.HandlerBuilder
import io.neyb.swak.handler.cross.route.Route
import io.neyb.swak.http.UpdatableRequest
import io.neyb.swak.http.Response
import io.reactivex.Single
import java.util.*

class Cross(
        private val routes: List<Route>
) : Handler<String> {

    override fun handle(request: UpdatableRequest<String>): Single<Response> {
        val acceptingRoutes = routes.filter { it.accept(request) }
        return when (acceptingRoutes.size) {
            0 -> Single.error { NoRouteFound(request.path) }
            1 -> acceptingRoutes.single().handle(request)
            else -> Single.error { SeveralRouteFound(request.path, acceptingRoutes) }
        }
    }

    class Builder : HandlerBuilder<String> {
        val routes: MutableList<Route> = ArrayList()
        override fun build() = Cross(routes)
    }
}

