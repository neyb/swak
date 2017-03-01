package io.neyb.swak.chain.route

import io.neyb.swak.chain.RequestHandler
import io.neyb.swak.chain.route.requestUpdater.RequestUpdater
import io.neyb.swak.chain.route.matcher.RequestMatcher
import io.neyb.swak.http.Request
import io.neyb.swak.http.Response
import io.reactivex.Single

class Route(
        val requestMatcher: RequestMatcher,
        val requestUpdater: RequestUpdater,
        val requestHandler: RequestHandler
) :
        RequestMatcher by requestMatcher,
        RequestHandler {
    companion object factory

    override fun handle(request: Request): Single<Response> =
            requestUpdater.update(request)
                    .flatMap { requestHandler.handle(it) }

    override fun toString() = "on \"$requestMatcher\""
}

