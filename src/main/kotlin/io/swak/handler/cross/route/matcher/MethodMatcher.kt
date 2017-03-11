package io.swak.handler.cross.route.matcher

import io.swak.http.Method
import io.swak.http.UpdatableRequest

class MethodMatcher<B>(private val method: Method) : RequestMatcher<B> {
    override fun accept(request: UpdatableRequest<B>) =
            request.method == method

    override fun toString() = method.toString()
}