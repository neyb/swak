package swak.handler.interceptor.before

import io.reactivex.Single
import swak.http.UpdatableRequest

interface BeforeInterceptor<I> {
    fun updateRequest(request: UpdatableRequest<I>): Single<UpdatableRequest<I>>
}