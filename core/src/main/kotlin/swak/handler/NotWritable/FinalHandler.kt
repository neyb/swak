package swak.handler.NotWritable

import swak.http.request.context.*
import swak.http.response.NotWritableResponse
import swak.http.response.context.NotWrittableResponseContext

internal class FinalHandler<IB, out OB>(private val handler: suspend (RequestContext<IB>) -> NotWritableResponse<OB>) : NotWritableHandler<IB, OB> {
    override suspend fun handle(reqContext: UpdatableRequestContext<IB>): NotWrittableResponseContext<IB, OB> =
            NotWrittableResponseContext(reqContext, handler(reqContext))
}
