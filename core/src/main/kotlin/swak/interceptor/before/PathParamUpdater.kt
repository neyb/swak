package swak.interceptor.before

import swak.handler.path.RoutePath
import swak.http.NoPathParamExtractor
import swak.http.request.UpdatableRequest

internal class PathParamUpdater(private val routePath: RoutePath) : RequestUpdater<String> {
    override fun updateRequest(request: UpdatableRequest<String>) =
            request.withPathParamExtractor(routePath.extractor ?: NoPathParamExtractor)

    override fun toString() = "update path for $routePath"
}