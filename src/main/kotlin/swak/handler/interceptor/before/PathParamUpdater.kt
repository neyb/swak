package swak.handler.interceptor.before

import io.reactivex.Single
import swak.handler.path.RoutePath
import swak.http.UpdatableRequest

internal class PathParamUpdater(private val routePath: RoutePath) : BeforeInterceptor<String> {
    override fun updateRequest(request: UpdatableRequest<String>) = Single.just(
            request.withPathParamExtractor(routePath.extractor))
}