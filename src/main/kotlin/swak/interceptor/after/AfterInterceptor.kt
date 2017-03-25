package swak.interceptor.after

import io.reactivex.Single
import swak.http.requestContext.UpdatableResponseContext

interface AfterInterceptor<Body> {
    fun updateRequestContext(respContext: UpdatableResponseContext<Body>): Single<UpdatableResponseContext<Body>>
}