package swak.interceptor.after

import swak.http.response.context.UpdatableResponseContext

interface AfterInterceptor<IB, OB> {
    fun updateRequestContext(respContext: UpdatableResponseContext<IB, OB>): UpdatableResponseContext<IB, OB>
}