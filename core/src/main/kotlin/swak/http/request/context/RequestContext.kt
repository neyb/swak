package swak.http.request.context

import swak.http.request.Request

interface RequestContext<out BodyIn> {
    val request: Request<BodyIn>
}