package swak.handler

import io.reactivex.Single
import swak.http.request.context.UpdatableRequestContext
import swak.http.response.context.UpdatableResponseContext

interface Handler<ReqBody> {
    fun handle(reqContext: UpdatableRequestContext<ReqBody>): Single<UpdatableResponseContext<ReqBody>>
}