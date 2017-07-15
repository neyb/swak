package swak.interceptor.before

import swak.http.request.UpdatableRequest
import java.util.*

internal class RequestUpdaters<T>(
        private val interceptors: List<RequestUpdater<T>>
) : RequestUpdater<T> {

    override fun updateRequest(request: UpdatableRequest<T>) =
            interceptors.fold(request) { resultingrequest, interceptor ->
                interceptor.updateRequest(resultingrequest)
            }

    class Builder<T> {
        val interceptors: MutableList<RequestUpdater<T>> = ArrayList()
        fun hasBehaviour() = !interceptors.isEmpty()
        fun build() = RequestUpdaters(interceptors.toList())
    }

    override fun toString() = interceptors.toString()
}