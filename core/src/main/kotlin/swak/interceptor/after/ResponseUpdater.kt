package swak.interceptor.after

import swak.http.request.Request
import swak.http.response.context.UpdatableResponseContext
import swak.http.response.WritableResponse

interface ResponseUpdater<IB, OB> : AfterInterceptor<IB, OB> {
    override fun updateRequestContext(respContext: UpdatableResponseContext<IB, OB>) =
            respContext.copy(response = onAfter(respContext.request, respContext.response))

    fun onAfter(request: Request<IB>, response: WritableResponse<OB>): WritableResponse<OB>
}