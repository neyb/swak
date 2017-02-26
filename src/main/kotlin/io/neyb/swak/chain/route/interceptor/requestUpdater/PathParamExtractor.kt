package io.neyb.swak.chain.route.interceptor.requestUpdater

import io.neyb.swak.chain.route.path.RoutePath
import io.neyb.swak.http.Request

class PathParamExtractor(private val routePath: RoutePath) : RequestUpdater {
    override fun update(request: Request) =
            request.withPathParam(routePath.extractPathParams(request.requestPath))
}