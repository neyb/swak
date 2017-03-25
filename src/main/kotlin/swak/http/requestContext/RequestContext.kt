package swak.http.requestContext

import swak.http.request.Request

interface RequestContext<out BodyIn> {
    val request: Request<BodyIn>
}