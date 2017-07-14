package swak.interceptor.after

import io.reactivex.Single
import swak.http.response.context.UpdatableResponseContext

interface AfterInterceptor<IB, OB> {
    fun updateRequestContext(respContext: UpdatableResponseContext<IB, OB>): Single<UpdatableResponseContext<IB, OB>>
}