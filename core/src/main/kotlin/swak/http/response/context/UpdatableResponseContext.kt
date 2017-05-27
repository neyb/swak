package swak.http.response.context

import swak.http.request.context.RequestContext
import swak.http.request.context.ResponseContext
import swak.http.response.WritableResponse

data class UpdatableResponseContext<out IB>(
        val requestContext: RequestContext<IB>,
        override val response: WritableResponse<*>
) : ResponseContext<IB, Any?>, RequestContext<IB> by requestContext {
    fun <NewReqBody> withRequestContext(requestContext: RequestContext<NewReqBody>) =
            UpdatableResponseContext(requestContext, response)
}