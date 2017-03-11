package swak.matcher

import swak.handler.path.RoutePath
import swak.http.UpdatableRequest

internal class PathMatcher<B>(private val path: RoutePath) : RequestMatcher<B> {
    override fun accept(request: UpdatableRequest<B>) = path.accept(request.path)

    override fun toString() = path.toString()
}