package io.swak.handler.cross.route.matcher

import io.swak.handler.path.RoutePath
import io.swak.http.UpdatableRequest

internal class PathMatcher<B>(private val path: RoutePath) : RequestMatcher<B> {
    override fun accept(request: UpdatableRequest<B>) = path.accept(request.path)

    override fun toString() = path.toString()
}