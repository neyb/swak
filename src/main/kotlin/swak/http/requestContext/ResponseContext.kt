package swak.http.requestContext

import swak.http.response.Response

interface ResponseContext<out BodyOut> : RequestContext<BodyOut> {
    val response: Response
}