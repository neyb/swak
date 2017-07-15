package swak.interceptor.before

import swak.http.request.context.UpdatableRequestContext

interface BeforeInterceptor<Body> {
    fun updateRequestContext(requestContext: UpdatableRequestContext<Body>):UpdatableRequestContext<Body>
}