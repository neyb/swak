package io.neyb.swak.chain

import io.neyb.swak.http.Request
import io.neyb.swak.http.Response
import io.reactivex.Single

@FunctionalInterface
interface RequestHandler {
    fun handle(request: Request): Single<Response>
}