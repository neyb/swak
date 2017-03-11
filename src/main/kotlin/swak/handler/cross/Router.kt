package swak.handler.cross

import io.reactivex.Single
import swak.handler.Handler
import swak.handler.HandlerBuilder
import swak.handler.cross.route.Route
import swak.http.Response
import swak.http.UpdatableRequest
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
