package io.neyb.swak.chain

import io.neyb.swak.chain.route.Route
import io.neyb.swak.chain.route.requestUpdater.PathParamExtractor
import io.neyb.swak.chain.route.matcher.MethodMatcher
import io.neyb.swak.chain.route.matcher.PathMatcher
import io.neyb.swak.chain.route.path.RoutePath
import io.neyb.swak.http.Method
import io.neyb.swak.http.Request
import io.neyb.swak.http.Response
import io.reactivex.Single

class ChainConfigurer(val chain: Chain) {

//    fun intercept()

//    infix fun Method.on(path:String) =
//            RouteConfigurer(this, path)

    fun handle(method: Method, path: String, handler: (Request) -> Single<Response>) {
        handle(method, path, handler.asRequestHandler())
    }

    fun handle(method: Method, path: String, handler: RequestHandler) {
        val routePath = RoutePath.of(path)
        chain.routes.add(Route(
                MethodMatcher(method) and PathMatcher(routePath),
                PathParamExtractor(routePath),
                handler))
    }

    private fun ((Request) -> Single<Response>).asRequestHandler() = object : RequestHandler {
        override fun handle(request: Request) = this@asRequestHandler(request)
    }

}