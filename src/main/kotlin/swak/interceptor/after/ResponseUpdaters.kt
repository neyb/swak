package swak.interceptor.after

import io.reactivex.Single
import swak.http.request.Request
import swak.http.response.Response

internal class ResponseUpdaters<B>(
        private val interceptors: List<ResponseUpdater<*>> = listOf()
) : ResponseUpdater<B> {

    override fun <B> onAfter(request: Request<B>, response: Response): Single<Response> {
        var result = Single.just(response)
        for (interceptor in interceptors)
            result = result.flatMap { interceptor.onAfter(request, response) }
        return result
    }

    override fun toString() = interceptors.toString()

    class Builder<B> {
        private val interceptors = mutableListOf<ResponseUpdater<*>>()

        fun build(): ResponseUpdaters<B> = ResponseUpdaters(interceptors)

        fun hasBehaviour() = false
    }
}