package swak.handler.router.route

import swak.handler.Handler
import swak.handler.router.Router
import swak.matcher.RequestMatcher

internal class Route(
        private val matcher: RequestMatcher<String>,
        private val handler: Handler<String, *>
) : RequestMatcher<String> by matcher,
        Handler<String, Any?> by handler{
    fun  isARouter() = handler is Router
    fun asRouter() = handler as Router
    override fun toString() = "$matcher => $handler"
}