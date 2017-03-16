package swak.interceptor.before

import io.reactivex.Single
import swak.http.request.UpdatableRequest
import java.util.*

internal class BeforeInterceptors<T>(
        private val interceptors: List<BeforeInterceptor<T>>
) : BeforeInterceptor<T> {

    override fun updateRequest(request: UpdatableRequest<T>): Single<UpdatableRequest<T>> {
        var result = Single.just(request)
        for (interceptor in interceptors)
            result = result.flatMap { interceptor.updateRequest(it) }
        return result
    }

    class Builder<T> {
        val interceptors: MutableList<BeforeInterceptor<T>> = ArrayList()
        fun hasBehaviour() = !interceptors.isEmpty()
        fun build() = BeforeInterceptors(interceptors.toList())
    }

    override fun toString() = interceptors.toString()
}