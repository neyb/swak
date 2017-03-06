package io.neyb.swak.handler.interceptor.before

import io.neyb.swak.http.Request
import io.reactivex.Single

interface BeforeInterceptor<I> {
    fun updateRequest(request: Request<I>): Single<Request<I>>
}