package io.neyb.swak.handler.cross.route

import io.neyb.swak.handler.Handler
import io.neyb.swak.handler.cross.route.matcher.RequestMatcher

class Route<Body>(
        private val matcher: RequestMatcher<Body>,
        private val handler: Handler<Body>
) : RequestMatcher<Body> by matcher,
        Handler<Body> by handler