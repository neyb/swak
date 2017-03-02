package io.neyb.swak.chain.route.matcher

import io.neyb.swak.http.Request

@FunctionalInterface
interface RequestMatcher {
    fun accept(request: Request<String>): Boolean

    infix fun or(matcher: RequestMatcher): RequestMatcher = OrMatcher(mutableListOf(this, matcher))

    infix fun and(matcher: RequestMatcher): RequestMatcher = AndMatcher(mutableListOf(this, matcher))
}