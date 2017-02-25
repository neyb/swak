package io.neyb.swak.core.chain

import io.neyb.swak.core.chain.route.Routes
import io.neyb.swak.core.http.Request
import io.neyb.swak.core.http.Response
import io.undertow.server.HttpServerExchange
import rx.Observable

class Chain {
    val routes : Routes = Routes()

    fun handle(request: Request) :Observable<Response> =
            routes.handle(request)

}

