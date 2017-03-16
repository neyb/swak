package swak.interceptor.before

import io.reactivex.Single
import swak.handler.path.RoutePath
import swak.http.NoPathParamExtractor
import swak.http.request.UpdatableRequest

internal class PathParamUpdater(private val routePath: RoutePath) : BeforeInterceptor<String> {
    override fun updateRequest(request: UpdatableRequest<String>) = Single.just(
            request.withPathParamExtractor(routePath.extractor?:NoPathParamExtractor))!!

    override fun toString() = "update path for $routePath"
}