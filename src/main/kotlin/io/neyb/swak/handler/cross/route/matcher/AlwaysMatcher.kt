package io.neyb.swak.handler.cross.route.matcher

import io.neyb.swak.http.Request

class AlwaysMatcher<B> :RequestMatcher<B> {
    override fun accept(request: Request<B>) = true
}