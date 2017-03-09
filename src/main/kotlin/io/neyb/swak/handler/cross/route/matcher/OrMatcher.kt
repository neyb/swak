package io.neyb.swak.handler.cross.route.matcher

import io.neyb.swak.http.UpdatableRequest

class OrMatcher<B>(private val matchers: MutableList<RequestMatcher<B>>) : RequestMatcher<B> {
    override fun accept(request: UpdatableRequest<B>) = matchers.any { it.accept(request) }

    override fun or(matcher: RequestMatcher<B>): RequestMatcher<B> {
        matchers += matcher
        return this
    }

    override fun toString() = matchers.joinToString(prefix = "(", separator = " or ", postfix = ")")
}