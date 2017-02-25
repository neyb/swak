package io.neyb.swak.core.chain.route

import io.neyb.swak.core.http.Request
import io.neyb.swak.core.http.Response
import io.neyb.swak.core.http.Status
import rx.Observable
import java.util.*

class Routes {
    private val routes: MutableList<Route> = ArrayList()

    fun add(route: Route) {
        routes.add(route)
    }

    fun handle(request: Request): Observable<Response> {
        val acceptingRoutes = routes.filter { it.accept(request) }
        return when (acceptingRoutes.size) {
            0 -> Observable.just(
                    Response(status = Status.NOT_FOUND))
            1 -> acceptingRoutes.single().handle(request)
            else -> Observable.just(
                    Response(Status.INTERNAL_ERROR))
        }
    }


}

