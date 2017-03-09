package io.neyb.swak.handler.interceptor.before

import io.neyb.swak.http.UpdatableRequest
import io.reactivex.Single
import java.util.*

class BeforeInterceptors<T>(
        private val interceptors: List<BeforeInterceptor<T>>
) : BeforeInterceptor<T> {

    override fun updateRequest(request: UpdatableRequest<T>): Single<UpdatableRequest<T>> {
        var result = Single.just(request)
        for (interceptor in interceptors)
            result = result.flatMap { interceptor.updateRequest(it) }
        return result
    }

    class Builder<T> {
        val interceptors: MutableList<BeforeInterceptor<T>> = ArrayList()
        fun build() = BeforeInterceptors(interceptors.toList())
    }
}