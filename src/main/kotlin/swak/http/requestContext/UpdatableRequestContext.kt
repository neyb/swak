package swak.http.requestContext

import swak.http.request.Request
import swak.http.response.Response

class UpdatableRequestContext<BodyIn>(
        override var request: Request<BodyIn>
) : RequestContext<BodyIn> {
    override var response: Response = Response()
}