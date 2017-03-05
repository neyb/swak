package io.neyb.swak.route.interceptors.after

import io.neyb.swak.http.Request
import io.neyb.swak.http.Response
import io.reactivex.Single

class AfterInterceptors<B> : AfterInterceptor<B> {
    private val interceptors = mutableListOf<AfterInterceptor<B>>()

    fun add(interceptor: AfterInterceptor<B>) {
        interceptors.add(interceptor)
    }

    override fun <B> onAfter(request: Request<B>, response: Response): Single<Response> {
        var result = Single.just(response)
        for (interceptor in interceptors)
            result = result.flatMap { interceptor.onAfter(request, response) }
        return result
    }
}