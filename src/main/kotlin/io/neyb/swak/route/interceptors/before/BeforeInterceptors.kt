package io.neyb.swak.route.interceptors.before

import io.neyb.swak.http.Request
import io.reactivex.Single
import java.util.*

class BeforeInterceptors<T>(
        private val interceptors: MutableList<BeforeInterceptor<T, T>> = ArrayList()
) : BeforeInterceptor<T, T> {

    fun add(interceptor: BeforeInterceptor<T, T>) {
        interceptors.add(interceptor)
    }

    override fun updateRequest(request: Request<T>): Single<Request<T>> {
        var result = Single.just(request)
        for (interceptor in interceptors)
            result = result.flatMap { interceptor.updateRequest(it) }
        return result
    }
}