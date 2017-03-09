package io.neyb.swak.handler

import io.neyb.swak.http.*
import io.reactivex.Single

class FinalHandler<B>(private val handler: (Request<B>) -> Single<Response>) : Handler<B> {
    override fun handle(request: UpdatableRequest<B>) = handler(request)
}