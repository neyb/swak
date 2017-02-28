package io.neyb.swak.chain.route

import io.neyb.swak.http.Request
import io.neyb.swak.http.Response
import io.reactivex.Single
import java.util.*

class Routes {
    private val routes: MutableList<Route> = ArrayList()

    fun add(route: Route) {
        routes.add(route)
    }

    fun handle(request: Request): Single<Response> {
        val acceptingRoutes = routes.filter { it.accept(request) }
        return when (acceptingRoutes.size) {
            0 -> Single.error { NoRouteFound(request.path) }
            1 -> acceptingRoutes.single().handle(request)
            else -> Single.error { SeveralRouteFound(request.path, acceptingRoutes) }
        }
    }


}

