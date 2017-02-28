package io.neyb.swak.chain.route.interceptor

import io.neyb.swak.http.Request
import io.neyb.swak.http.Response
import io.reactivex.Single

interface Interceptor {
    fun onBefore(request: Request): Single<Request> =
            Single.just(request)

    fun onAfter(request: Request, response: Response): Single<Response> =
            Single.just(response)

    fun onError(error: Throwable): Response? =
            throw error
}