package io.swak.handler.interceptor.before

import io.swak.http.UpdatableRequest
import io.reactivex.Single

interface BeforeInterceptor<I> {
    fun updateRequest(request: UpdatableRequest<I>): Single<UpdatableRequest<I>>
}