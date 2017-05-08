package swak.handler

import io.reactivex.Single
import swak.http.request.Request
import swak.http.request.context.UpdatableRequestContext
import swak.http.response.NotWritableResponse
import swak.http.response.context.NotWrittableResponseContext

internal class FinalHandler<IB, out OB : Any>(private val handler: (Request<IB>) -> Single<out NotWritableResponse<OB>>) : NotWritableHandler<IB, OB> {
    override fun handle(reqContext: UpdatableRequestContext<IB>): Single<out NotWrittableResponseContext<IB, OB>> {
        return handler(reqContext.request)
                .map { NotWrittableResponseContext(reqContext, it) }
    }
}
