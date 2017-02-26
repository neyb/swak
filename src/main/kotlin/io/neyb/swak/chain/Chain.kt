package io.neyb.swak.chain

import io.neyb.swak.chain.route.Routes
import io.neyb.swak.http.Request
import io.neyb.swak.http.Response
import io.reactivex.Single

class Chain {
    val routes : Routes = Routes()

    fun handle(request: Request) : Single<Response> =
            routes.handle(request)

}

