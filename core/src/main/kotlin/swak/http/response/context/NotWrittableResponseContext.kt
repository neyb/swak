package swak.http.response.context

import swak.body.writer.BodyWriter
import swak.http.request.context.RequestContext
import swak.http.request.context.ResponseContext
import swak.http.response.*

class NotWrittableResponseContext<out ReqBody, out RespBody>(
        val requestContext: RequestContext<ReqBody>,
        override val response: NotWritableResponse<RespBody>
) : ResponseContext<ReqBody, RespBody>, RequestContext<ReqBody> by requestContext {

    fun withWriter(writer: BodyWriter<RespBody>) = withResponse(response.withWriter(writer))

    private fun <NewRespBody> withResponse(response: WritableResponse<NewRespBody>) =
            UpdatableResponseContext(requestContext, response)
}