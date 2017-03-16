package swak.http.requestContext

import swak.http.request.Request
import swak.http.response.Response

interface RequestContext<BodyIn> {
    val request: Request<BodyIn>
    val response: Response
}