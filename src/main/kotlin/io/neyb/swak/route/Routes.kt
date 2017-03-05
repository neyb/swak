package io.neyb.swak.route

import io.neyb.swak.http.Request
import io.neyb.swak.http.Response
import io.reactivex.Single
import java.util.*

class Routes<Body>(
        private val routes: MutableList<Route<Body>>
) : Route<Body> {

    override fun accept(request: Request<Body>) = true

    override fun handle(request: Request<Body>): Single<Response> {
        val acceptingRoutes = routes.filter { it.accept(request) }
        return when (acceptingRoutes.size) {
            0 -> Single.error { NoRouteFound(request.path) }
            1 -> acceptingRoutes.single().handle(request)
            else -> Single.error { SeveralRouteFound(request.path, acceptingRoutes) }
        }
    }


}

