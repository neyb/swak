package swak.handler

import io.reactivex.Single
import swak.http.request.Request
import swak.http.requestContext.UpdatableRequestContext
import swak.http.requestContext.UpdatableResponseContext
import swak.http.response.Response

internal class FinalHandler<Body>(private val handler: (Request<Body>) -> Single<Response>) : Handler<Body> {
    override fun handle(reqContext: UpdatableRequestContext<Body>) = handler(reqContext.request)
            .map { UpdatableResponseContext(reqContext, it) }!!
}