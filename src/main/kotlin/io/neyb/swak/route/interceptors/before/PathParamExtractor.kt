package io.neyb.swak.route.interceptors.before

import io.neyb.swak.route.interceptors.before.BeforeInterceptor
import io.neyb.swak.route.path.RoutePath
import io.neyb.swak.http.Request
import io.reactivex.Single

class PathParamExtractor<Body>(private val routePath: RoutePath) : BeforeInterceptor<Body, Body> {
    override fun updateRequest(request: Request<Body>): Single<Request<Body>> = Single.just(
            request.withPathParam(routePath.extractPathParams(request.path)))
}

class PathParamExtractor(private val routePath: RoutePath) : BeforeInterceptor<String, String> {
    override fun updateRequest(request: Request<String>) = Single.just(
            request.withPathParam(routePath.extractPathParams(request.path)))
}