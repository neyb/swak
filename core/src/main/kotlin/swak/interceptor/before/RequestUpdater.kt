package swak.interceptor.before

import swak.http.request.UpdatableRequest
import swak.http.request.context.UpdatableRequestContext

interface RequestUpdater<I> : BeforeInterceptor<I> {
    override fun updateRequestContext(requestContext: UpdatableRequestContext<I>) =
            requestContext.copy(request = updateRequest(requestContext.request))

    fun updateRequest(request: UpdatableRequest<I>): UpdatableRequest<I>
}