package io.neyb.swak.route

import io.neyb.swak.route.RequestHandler
import io.neyb.swak.http.Request

class FinalRoute<Body>(
        private val requestHandler: RequestHandler<Body>
) :
        Route<Body>,
        RequestHandler<Body> by requestHandler
{
    override fun accept(request: Request<Body>) = true
}

