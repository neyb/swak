package io.neyb.swak.route.matcher

import io.neyb.swak.http.Request

@FunctionalInterface
interface RequestMatcher<B> {
    fun accept(request: Request<B>): Boolean

    infix fun or(matcher: RequestMatcher<B>): RequestMatcher<B> = OrMatcher(mutableListOf(this, matcher))

    infix fun and(matcher: RequestMatcher<B>): RequestMatcher<B> = AndMatcher(mutableListOf(this, matcher))
}