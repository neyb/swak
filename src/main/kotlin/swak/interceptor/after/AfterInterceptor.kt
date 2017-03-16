package swak.interceptor.after

import io.reactivex.Single
import swak.http.response.Response
import swak.http.request.UpdatableRequest

interface AfterInterceptor<in B> {
    fun <B> onAfter(request: UpdatableRequest<B>, response: Response): Single<Response>
}