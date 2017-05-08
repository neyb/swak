package swak.handler

import io.reactivex.Single
import swak.http.request.context.UpdatableRequestContext
import swak.http.response.context.NotWrittableResponseContext

interface NotWritableHandler<ReqBody, out OB : Any> {
    fun handle(reqContext: UpdatableRequestContext<ReqBody>): Single<out NotWrittableResponseContext<ReqBody, OB>>
}