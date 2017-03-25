package swak.interceptor.before

import io.reactivex.Single
import swak.http.request.UpdatableRequest
import swak.http.requestContext.UpdatableRequestContext

interface RequestUpdater<I> : BeforeInterceptor<I> {
    override fun updateRequestContext(requestContext: UpdatableRequestContext<I>) =
        updateRequest(requestContext.request)
                .map { requestContext.copy(request = it) }

    fun updateRequest(request: UpdatableRequest<I>): Single<UpdatableRequest<I>>
}