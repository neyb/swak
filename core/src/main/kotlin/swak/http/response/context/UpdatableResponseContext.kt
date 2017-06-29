package swak.http.response.context

import swak.http.request.context.RequestContext
import swak.http.request.context.ResponseContext
import swak.http.response.WritableResponse

data class UpdatableResponseContext<out IB, out OB>(
        val requestContext: RequestContext<IB>,
        override val response: WritableResponse<OB>
) : ResponseContext<IB, OB>, RequestContext<IB> by requestContext {
    fun <NewReqBody> withRequestContext(requestContext: RequestContext<NewReqBody>) =
            UpdatableResponseContext(requestContext, response)
}