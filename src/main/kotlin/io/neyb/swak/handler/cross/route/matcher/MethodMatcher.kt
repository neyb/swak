package io.neyb.swak.handler.cross.route.matcher

import io.neyb.swak.http.Method
import io.neyb.swak.http.UpdatableRequest

class MethodMatcher<B>(private val method: Method) : RequestMatcher<B> {
    override fun accept(request: UpdatableRequest<B>) =
            request.method == method

    override fun toString() = method.toString()
}