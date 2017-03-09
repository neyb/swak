package io.neyb.swak.handler.cross.route.matcher

import io.neyb.swak.http.UpdatableRequest

class AlwaysMatcher<B> :RequestMatcher<B> {
    override fun accept(request: UpdatableRequest<B>) = true
}