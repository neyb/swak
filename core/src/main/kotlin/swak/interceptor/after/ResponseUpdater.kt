package swak.interceptor.after

import io.reactivex.Single
import swak.http.request.Request
import swak.http.response.context.UpdatableResponseContext
import swak.http.response.WritableResponse

interface ResponseUpdater<IB, OB> : AfterInterceptor<IB, OB> {
    override fun updateRequestContext(respContext: UpdatableResponseContext<IB, OB>) =
            onAfter(respContext.request, respContext.response)
                    .map { respContext.copy(response = it) }!!

    fun onAfter(request: Request<IB>, response: WritableResponse<OB>): Single<WritableResponse<OB>>
}