package swak.interceptor.before

import io.reactivex.Single
import swak.http.request.UpdatableRequest
import java.util.*

internal class RequestUpdaters<T>(
        private val interceptors: List<RequestUpdater<T>>
) : RequestUpdater<T> {

    override fun updateRequest(request: UpdatableRequest<T>): Single<UpdatableRequest<T>> {
        var result = Single.just(request)
        for (interceptor in interceptors)
            result = result.flatMap { interceptor.updateRequest(it) }
        return result
    }

    class Builder<T> {
        val interceptors: MutableList<RequestUpdater<T>> = ArrayList()
        fun hasBehaviour() = !interceptors.isEmpty()
        fun build() = RequestUpdaters(interceptors.toList())
    }

    override fun toString() = interceptors.toString()
}