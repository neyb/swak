package swak.interceptor.before

import io.reactivex.Single
import swak.http.request.UpdatableRequest

interface BeforeInterceptor<I> {
    fun updateRequest(request: UpdatableRequest<I>): Single<UpdatableRequest<I>>
}