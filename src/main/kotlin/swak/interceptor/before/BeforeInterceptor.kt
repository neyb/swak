package swak.interceptor.before

import io.reactivex.Single
import swak.http.request.context.UpdatableRequestContext

interface BeforeInterceptor<Body> {
    fun updateRequestContext(requestContext: UpdatableRequestContext<Body>):Single<UpdatableRequestContext<Body>>
}