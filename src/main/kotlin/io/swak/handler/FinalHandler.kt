package io.swak.handler

import io.swak.http.*
import io.reactivex.Single
import io.swak.http.Request
import io.swak.http.UpdatableRequest

internal class FinalHandler<B>(private val handler: (Request<B>) -> Single<Response>) : Handler<B> {
    override fun handle(request: UpdatableRequest<B>) = handler(request)
}