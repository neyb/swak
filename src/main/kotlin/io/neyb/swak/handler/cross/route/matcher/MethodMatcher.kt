package io.neyb.swak.handler.cross.route.matcher

import io.neyb.swak.http.Method
import io.neyb.swak.http.Request

class MethodMatcher<B>(private val method: Method) : RequestMatcher<B> {
    override fun accept(request: Request<B>) =
            request.method == method

    override fun toString() = method.toString()
}