package swak.handler.router

import io.reactivex.Single
import swak.handler.*
import swak.handler.router.route.Route
import swak.http.request.context.UpdatableRequestContext
import swak.http.response.context.UpdatableResponseContext
import java.util.*

internal class Router(
        private val routes: List<Route>
) : Handler<String, Any?> {
    override fun handle(reqContext: UpdatableRequestContext<String>): Single<UpdatableResponseContext<String, Any?>> {
        val request = reqContext.request
        val acceptingRoutes = routes.filter { it.accept(request) }
        return when (acceptingRoutes.size) {
            0 -> Single.error { NoRouteFound(request.path) }
            1 -> acceptingRoutes.single().handle(reqContext)
            else -> Single.error { SeveralRouteFound(request.path, acceptingRoutes) }
        }
    }

    override fun toString() = routes.toString()

    class Builder : HandlerBuilder<String, Any?> {
        val routes: MutableList<Route> = ArrayList()
        override fun build(): Router {
            if (routes.size == 0) throw NoRouteProvided()
            if (routes.size == 1 && routes[0].isARouter())
                return routes[0].asRouter()
            return Router(routes)
        }
    }
}

