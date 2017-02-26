package io.neyb.swak.chain.route.matcher

import io.neyb.swak.chain.route.path.RoutePath
import io.neyb.swak.http.Request

class PathMatcher(private val path:RoutePath): RequestMatcher {
    override fun accept(request: Request) = path.accept(request.requestPath)
}