package swak.handler

import io.reactivex.Single
import swak.http.*
import swak.http.request.Request
import swak.http.request.UpdatableRequest
import swak.http.response.Response

internal class FinalHandler<B>(private val handler: (Request<B>) -> Single<Response>) : Handler<B> {
    override fun handle(request: UpdatableRequest<B>) = handler(request)
}