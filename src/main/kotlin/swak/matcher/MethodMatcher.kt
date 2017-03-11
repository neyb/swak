package swak.matcher

import swak.http.Method
import swak.http.UpdatableRequest

class MethodMatcher<B>(private val method: Method) : RequestMatcher<B> {
    override fun accept(request: UpdatableRequest<B>) =
            request.method == method

    override fun toString() = method.toString()
}