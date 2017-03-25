package swak.http.requestContext

import swak.http.response.Response

data class UpdatableResponseContext<out ReqBodyOut>(
        val requestContext: RequestContext<ReqBodyOut>,
        override val response: Response
) : ResponseContext<ReqBodyOut>, RequestContext<ReqBodyOut> by requestContext