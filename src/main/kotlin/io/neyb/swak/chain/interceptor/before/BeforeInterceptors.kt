package io.neyb.swak.chain.interceptor.before

import io.neyb.swak.http.Request
import io.reactivex.Single
import java.util.*

class BeforeInterceptors : BeforeInterceptor {
    private val interceptors: MutableList<BeforeInterceptor> = ArrayList()

    fun add(interceptor: BeforeInterceptor) {
        interceptors.add(interceptor)
    }

    override fun onBefore(request: Request): Single<Request> {
        var result = Single.just(request)
        for (interceptor in interceptors)
            result = result.flatMap { interceptor.onBefore(it) }
        return result
    }
}