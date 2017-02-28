package io.neyb.swak.chain.route.interceptor

import io.neyb.swak.http.Request
import io.neyb.swak.http.Response
import io.reactivex.Single
import java.util.*

class Interceptors : Interceptor {
    private val interceptors: MutableList<Interceptor> = ArrayList()

    fun add(interceptor: Interceptor) {
        interceptors.add(interceptor)
    }

    override fun onBefore(request: Request): Single<Request> {
        var result = Single.just(request)
        for (interceptor in interceptors)
            result = result.flatMap { interceptor.onBefore(it) }
        return result
    }

    override fun onAfter(request: Request, response: Response): Single<Response> {
        var result = Single.just(response)
        for (interceptor in interceptors)
            result = result.flatMap { interceptor.onAfter(request, response) }
        return result
    }

    override fun onError(error: Throwable): Response? =
            interceptors.asSequence()
                    .mapNotNull { it.onError(error) }
                    .firstOrNull()
}