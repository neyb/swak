package swak.handler.NotWritable

import swak.http.response.context.NotWrittableResponseContext

interface NotWritableHandler<ReqBody, out OB> {
    suspend fun handle(reqContext: swak.http.request.context.UpdatableRequestContext<ReqBody>): NotWrittableResponseContext<ReqBody, OB>
}