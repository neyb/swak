package swak.handler.NotWritable

import io.reactivex.Single
import swak.http.request.context.*
import swak.http.response.NotWritableResponse
import swak.http.response.context.NotWrittableResponseContext

internal class FinalHandler<IB, out OB : Any>(private val handler: (RequestContext<IB>) -> Single<out NotWritableResponse<OB>>) : NotWritableHandler<IB, OB> {
    override fun handle(reqContext: UpdatableRequestContext<IB>): Single<out NotWrittableResponseContext<IB, OB>> {
        return handler(reqContext)
                .map { NotWrittableResponseContext(reqContext, it) }
    }
}
