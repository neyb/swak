package swak.matcher

import swak.http.request.Method
import swak.http.request.UpdatableRequest

class MethodMatcher<B>(private val method: Method) : RequestMatcher<B> {
    override fun accept(request: UpdatableRequest<B>) =
            request.method == method

    override fun toString() = method.toString()
}