package io.neyb.swak.handler.cross.route

import io.neyb.swak.handler.Handler
import io.neyb.swak.handler.cross.route.matcher.RequestMatcher

class Route(
        private val matcher: RequestMatcher<String>,
        private val handler: Handler<String>
) : RequestMatcher<String> by matcher,
        Handler<String> by handler