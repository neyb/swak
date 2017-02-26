package io.neyb.swak.chain.route.matcher

import io.neyb.swak.http.Request

class AndMatcher(private val matchers: MutableList<RequestMatcher>) : RequestMatcher {
    override fun accept(request: Request) = matchers.all { it.accept(request) }

    override fun and(matcher: RequestMatcher): RequestMatcher {
        matchers += matcher
        return this
    }
}