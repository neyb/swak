package swak.handler

import io.reactivex.Single
import swak.http.requestContext.UpdatableRequestContext
import swak.http.requestContext.UpdatableResponseContext

interface Handler<ReqBodyIn> {
    fun handle(reqContext: UpdatableRequestContext<ReqBodyIn>): Single<UpdatableResponseContext<ReqBodyIn>>
}