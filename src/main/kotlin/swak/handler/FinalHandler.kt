package swak.handler

import io.reactivex.Single
import swak.http.*

internal class FinalHandler<B>(private val handler: (Request<B>) -> Single<Response>) : Handler<B> {
    override fun handle(request: UpdatableRequest<B>) = handler(request)
}