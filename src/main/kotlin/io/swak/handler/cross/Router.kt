package io.swak.handler.cross

import io.swak.handler.Handler
import io.swak.handler.HandlerBuilder
import io.swak.handler.cross.route.Route
import io.swak.http.UpdatableRequest
import io.swak.http.Response
import io.reactivex.Single
import java.util.*

internal class Router(
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
        override fun build() = Router(routes)
    }
}

