package io.neyb.swak.chain.route.matcher

import io.neyb.swak.http.Method
import io.neyb.swak.http.Request

class MethodMatcher(private val method: Method) : RequestMatcher {
    override fun accept(request: Request) =
            request.method == method
}