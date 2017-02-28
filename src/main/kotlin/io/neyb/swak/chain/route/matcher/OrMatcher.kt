package io.neyb.swak.chain.route.matcher

import io.neyb.swak.http.Request

class OrMatcher(private val matchers: MutableList<RequestMatcher>) : RequestMatcher {
    override fun accept(request: Request) = matchers.any { it.accept(request) }

    override fun or(matcher: RequestMatcher): RequestMatcher {
        matchers += matcher
        return this
    }

    override fun toString() = matchers.joinToString(prefix = "(", separator = " or ", postfix = ")")
}