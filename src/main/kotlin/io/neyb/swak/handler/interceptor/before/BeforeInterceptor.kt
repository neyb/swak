package io.neyb.swak.handler.interceptor.before

import io.neyb.swak.http.UpdatableRequest
import io.reactivex.Single

interface BeforeInterceptor<I> {
    fun updateRequest(request: UpdatableRequest<I>): Single<UpdatableRequest<I>>
}