package io.neyb.swak.handler.interceptor.before

import io.neyb.swak.handler.path.RoutePath
import io.neyb.swak.http.Request
import io.reactivex.Single

class PathParamExtractor(private val routePath: RoutePath) : BeforeInterceptor<String> {
    override fun updateRequest(request: Request<String>) = Single.just(
            request.withPathParam(routePath.extractPathParams(request.path)))!!
}