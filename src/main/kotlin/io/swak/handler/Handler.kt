package io.swak.handler

import io.swak.http.UpdatableRequest
import io.swak.http.Response
import io.reactivex.Single

interface Handler<B> {
    fun handle(request: UpdatableRequest<B>): Single<Response>
}