package swak.interceptor.before

import io.reactivex.Single
import swak.http.requestContext.UpdatableRequestContext

interface BeforeInterceptor<Body> {
    fun updateRequestContext(requestContext: UpdatableRequestContext<Body>):Single<UpdatableRequestContext<Body>>
}