package swak.interceptor.after

import io.reactivex.Single
import swak.http.Response
import swak.http.UpdatableRequest

interface AfterInterceptor<in B> {
    fun <B> onAfter(request: UpdatableRequest<B>, response: Response): Single<Response>
}