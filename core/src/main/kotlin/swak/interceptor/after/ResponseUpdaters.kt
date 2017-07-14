package swak.interceptor.after

import io.reactivex.Single
import swak.http.request.Request
import swak.http.response.WritableResponse

internal class ResponseUpdaters<IB, OB>(
        private val interceptors: List<ResponseUpdater<IB, OB>> = listOf()
) : ResponseUpdater<IB, OB> {
    override fun onAfter(request: Request<IB>, response: WritableResponse<OB>): Single<WritableResponse<OB>> {
        var result = Single.just(response)
        for (interceptor in interceptors)
            result = result.flatMap { interceptor.onAfter(request, response) }
        return result
    }

    override fun toString() = interceptors.toString()

    class Builder<IB, OB> {
        private val interceptors = mutableListOf<ResponseUpdater<IB, OB>>()

        fun build(): ResponseUpdaters<IB, OB> = ResponseUpdaters(interceptors)

        fun hasBehaviour() = false
    }
}