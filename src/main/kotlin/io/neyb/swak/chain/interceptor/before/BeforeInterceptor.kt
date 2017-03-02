package io.neyb.swak.chain.interceptor.before

import io.neyb.swak.http.Request
import io.reactivex.Single

interface BeforeInterceptor<I, O> {
    fun updateRequest(request: Request<I>): Single<Request<O>>
}