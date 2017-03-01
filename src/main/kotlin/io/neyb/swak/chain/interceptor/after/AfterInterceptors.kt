package io.neyb.swak.chain.interceptor.before

import io.neyb.swak.http.Request
import io.neyb.swak.http.Response
import io.reactivex.Single
import org.omg.PortableInterceptor.Interceptor
import java.util.*

class AfterInterceptors : AfterInterceptor {
    private val interceptors = mutableListOf<AfterInterceptor>()

    fun add(interceptor: AfterInterceptor) {
        interceptors.add(interceptor)
    }

    override fun onAfter(request: Request, response: Response): Single<Response> {
        var result = Single.just(response)
        for (interceptor in interceptors)
            result = result.flatMap { interceptor.onAfter(request, response) }
        return result
    }
}