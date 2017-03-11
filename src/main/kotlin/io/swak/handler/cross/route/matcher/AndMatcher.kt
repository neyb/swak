package io.swak.handler.cross.route.matcher

import io.swak.http.UpdatableRequest

class AndMatcher<B>(private val matchers: MutableList<RequestMatcher<B>>) : RequestMatcher<B> {
    override fun accept(request: UpdatableRequest<B>) = matchers.all { it.accept(request) }

    override fun and(matcher: RequestMatcher<B>): RequestMatcher<B> {
        matchers += matcher
        return this
    }

    override fun toString() = matchers.joinToString(prefix = "(", separator = " and ", postfix = ")")
}