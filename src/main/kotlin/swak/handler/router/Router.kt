package swak.handler.router

import io.reactivex.Single
import swak.handler.Handler
import swak.handler.HandlerBuilder
import swak.handler.router.route.Route
import swak.http.requestContext.UpdatableRequestContext
import swak.http.requestContext.UpdatableResponseContext
import java.util.*

internal class Router(
        private val routes: List<Route>
) : Handler<String> {
    override fun handle(reqContext: UpdatableRequestContext<String>): Single<UpdatableResponseContext<String>> {
        val request = reqContext.request
        val acceptingRoutes = routes.filter { it.accept(request) }
        return when (acceptingRoutes.size) {
            0 -> Single.error { NoRouteFound(request.path) }
            1 -> acceptingRoutes.single().handle(reqContext)
            else -> Single.error { SeveralRouteFound(request.path, acceptingRoutes) }
        }
    }

    override fun toString() = routes.toString()

    class Builder : HandlerBuilder<String> {
        val routes: MutableList<Route> = ArrayList()
        override fun build() : Router{
            if(routes.size == 0) throw NoRouteProvided()
            if(routes.size == 1 && routes[0].isARouter())
                return routes[0].asRouter()
            return Router(routes)
        }
    }
}

