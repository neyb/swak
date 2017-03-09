package io.neyb.swak.handler

import io.neyb.swak.http.UpdatableRequest
import io.neyb.swak.http.Response
import io.reactivex.Single

interface Handler<B> {
    fun handle(request: UpdatableRequest<B>): Single<Response>
}