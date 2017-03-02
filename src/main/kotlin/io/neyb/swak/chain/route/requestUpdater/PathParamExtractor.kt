package io.neyb.swak.chain.route.requestUpdater

import io.neyb.swak.chain.interceptor.before.BeforeInterceptor
import io.neyb.swak.chain.route.path.RoutePath
import io.neyb.swak.http.Request
import io.reactivex.Single

class PathParamExtractor(private val routePath: RoutePath) : BeforeInterceptor<String, String> {
    override fun updateRequest(request: Request<String>) = Single.just(
            request.withPathParam(routePath.extractPathParams(request.path)))
}