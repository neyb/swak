package swak.interceptor.after

import io.reactivex.Single
import swak.http.request.Request
import swak.http.response.SimpleResponse
import swak.http.response.WritableResponse

internal class ResponseUpdaters<IB>(
        private val interceptors: List<ResponseUpdater<IB>> = listOf()
) : ResponseUpdater<IB> {
    override fun onAfter(request: Request<IB>, response: WritableResponse<Any>): Single<WritableResponse<Any>> {
        var result = Single.just(response)
        for (interceptor in interceptors)
            result = result.flatMap { interceptor.onAfter(request, response) }
        return result
    }

    override fun toString() = interceptors.toString()

    class Builder<IB> {
        private val interceptors = mutableListOf<ResponseUpdater<IB>>()

        fun build(): ResponseUpdaters<IB> = ResponseUpdaters(interceptors)

        fun hasBehaviour() = false
    }
}