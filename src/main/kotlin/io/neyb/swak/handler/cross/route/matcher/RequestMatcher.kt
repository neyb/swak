package io.neyb.swak.handler.cross.route.matcher

import io.neyb.swak.http.UpdatableRequest

@FunctionalInterface
interface RequestMatcher<B> {
    fun accept(request: UpdatableRequest<B>): Boolean

    infix fun or(matcher: RequestMatcher<B>): RequestMatcher<B> = OrMatcher(mutableListOf(this, matcher))

    infix fun and(matcher: RequestMatcher<B>): RequestMatcher<B> = AndMatcher(mutableListOf(this, matcher))
}