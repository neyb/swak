package io.neyb.swak.handler.interceptor.before

import io.neyb.swak.handler.path.RoutePath
import io.neyb.swak.http.UpdatableRequest
import io.reactivex.Single

internal class PathParamUpdater(private val routePath: RoutePath) : BeforeInterceptor<String> {
    override fun updateRequest(request: UpdatableRequest<String>) = Single.just(
            request.withPathParamExtractor(routePath.extractor))
}