package io.neyb.swak.handler.cross.route

import io.neyb.swak.handler.RequestHandler
import io.neyb.swak.handler.cross.route.matcher.RequestMatcher

class Route<Body>(
        private val matcher: RequestMatcher<Body>,
        private val handler: RequestHandler<Body>
) : RequestMatcher<Body> by matcher,
        RequestHandler<Body> by handler