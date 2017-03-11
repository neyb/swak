package swak.handler.cross.route

import swak.handler.Handler
import swak.handler.cross.route.matcher.RequestMatcher

internal class Route(
        private val matcher: RequestMatcher<String>,
        private val handler: Handler<String>
) : RequestMatcher<String> by matcher,
        Handler<String> by handler