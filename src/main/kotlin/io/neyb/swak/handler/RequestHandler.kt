package io.neyb.swak.handler

import io.neyb.swak.http.Request
import io.neyb.swak.http.Response
import io.reactivex.Single

interface RequestHandler<B> {
    fun handle(request: Request<B>): Single<Response>
}