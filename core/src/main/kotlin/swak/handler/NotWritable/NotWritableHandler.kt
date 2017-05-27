package swak.handler.NotWritable

import swak.http.response.context.NotWrittableResponseContext

interface NotWritableHandler<ReqBody, out OB> {
    fun handle(reqContext: swak.http.request.context.UpdatableRequestContext<ReqBody>): io.reactivex.Single<out NotWrittableResponseContext<ReqBody, OB>>
}