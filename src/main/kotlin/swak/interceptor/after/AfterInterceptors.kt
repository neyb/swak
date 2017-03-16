package swak.interceptor.after

import io.reactivex.Single
import swak.http.response.Response
import swak.http.request.UpdatableRequest

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

    override fun toString() = interceptors.toString()

    class Builder<B> {
        fun build() = AfterInterceptors<B>(

        )

        fun  hasBehaviour() = false
    }
}