package io.neyb.swak.chain.route.requestUpdater

import io.neyb.swak.chain.route.path.RoutePath
import io.neyb.swak.http.Request
import io.reactivex.Single

class PathParamExtractor(private val routePath: RoutePath) : RequestUpdater {
    override fun update(request: Request) = Single.just(
            request.withPathParam(routePath.extractPathParams(request.path)))
}