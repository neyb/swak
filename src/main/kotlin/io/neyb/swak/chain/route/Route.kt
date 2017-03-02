package io.neyb.swak.chain.route

import io.neyb.swak.chain.RequestHandler
import io.neyb.swak.chain.route.interceptors.body.reader.BeforeRouteInterceptor
import io.neyb.swak.chain.route.matcher.RequestMatcher
import io.neyb.swak.http.Request
import io.neyb.swak.http.Response
import io.reactivex.Single

class Route<B>(
        val requestMatcher: RequestMatcher,
        val beforeRouteInterceptor: BeforeRouteInterceptor<B>,
        val requestHandler: RequestHandler<B>
) :
        RequestMatcher by requestMatcher,
        RequestHandler<String> {
    companion object factory

    override fun handle(request: Request<String>): Single<Response> =
            beforeRouteInterceptor.updateRequest(request)
                    .flatMap { requestHandler.handle(it) }

    override fun toString() = "on \"$requestMatcher\""
}

