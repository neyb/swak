package io.neyb.swak.route

import io.neyb.swak.route.RequestHandler
import io.neyb.swak.route.matcher.RequestMatcher

interface Route<Body> : RequestMatcher<Body>, RequestHandler<Body> {

}