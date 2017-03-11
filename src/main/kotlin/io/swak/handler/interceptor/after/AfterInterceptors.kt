package io.swak.handler.interceptor.after

import io.swak.http.UpdatableRequest
import io.swak.http.Response
import io.reactivex.Single

internal class AfterInterceptors<B> : AfterInterceptor<B> {
    private val interceptors = mutableListOf<AfterInterceptor<B>>()

    fun add(interceptor: AfterInterceptor<B>) {
        interceptors.add(interceptor)
    }

    override fun <B> onAfter(request: UpdatableRequest<B>, response: Response): Single<Response> {
        var result = Single.just(response)
        for (interceptor in interceptors)
            result = result.flatMap { interceptor.onAfter(request, response) }
        return result
    }

    class Builder<B> {
        fun build() = AfterInterceptors<B>(

        )

        fun  hasBehaviour() = false
    }
}