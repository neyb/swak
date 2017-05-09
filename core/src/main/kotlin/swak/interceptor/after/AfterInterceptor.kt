package swak.interceptor.after

import io.reactivex.Single
import swak.http.response.context.UpdatableResponseContext

interface AfterInterceptor<IB> {
    fun updateRequestContext(respContext: UpdatableResponseContext<IB>): Single<UpdatableResponseContext<IB>>
}