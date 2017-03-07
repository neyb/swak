package io.neyb.swak.handler.cross.route.matcher

import io.neyb.swak.http.Request

class AndMatcher<B>(private val matchers: MutableList<RequestMatcher<B>>) : RequestMatcher<B> {
    override fun accept(request: Request<B>) = matchers.all { it.accept(request) }

    override fun and(matcher: RequestMatcher<B>): RequestMatcher<B> {
        matchers += matcher
        return this
    }

    override fun toString() = matchers.joinToString(prefix = "(", separator = " and ", postfix = ")")
}