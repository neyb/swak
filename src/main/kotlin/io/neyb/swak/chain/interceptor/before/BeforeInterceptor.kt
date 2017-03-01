package io.neyb.swak.chain.interceptor.before

import io.neyb.swak.http.Request
import io.reactivex.Single

interface BeforeInterceptor {
    fun onBefore(request: Request): Single<Request>
}