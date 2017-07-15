package swak.handler

import swak.http.request.context.UpdatableRequestContext
import swak.http.response.context.UpdatableResponseContext

interface Handler<ReqBody, out Out> {
    suspend fun handle(reqContext: UpdatableRequestContext<ReqBody>): UpdatableResponseContext<ReqBody, Out>
}