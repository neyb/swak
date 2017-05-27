package swak.interceptor.after

import io.reactivex.Single
import swak.http.request.Request
import swak.http.response.context.UpdatableResponseContext
import swak.http.response.WritableResponse

//FIXME shitty generics in this class
interface ResponseUpdater<IB> : AfterInterceptor<IB> {
    override fun updateRequestContext(respContext: UpdatableResponseContext<IB>) =
            onAfter(respContext.request, respContext.response)
                    .map { respContext.copy(response = it) }!!

    fun onAfter(request: Request<IB>, response: WritableResponse<*>): Single<WritableResponse<*>>
}