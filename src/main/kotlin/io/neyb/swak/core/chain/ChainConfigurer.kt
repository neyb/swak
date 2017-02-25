package io.neyb.swak.core.chain

import io.neyb.swak.core.chain.route.Route
import io.neyb.swak.core.chain.route.get
import io.neyb.swak.core.http.Request
import io.neyb.swak.core.http.Response
import rx.Observable

class ChainConfigurer(val chain: Chain) {
    fun get(path: String, handler: (Request) -> Observable<Response>) {
        chain.routes.add(Route.get(path, handler))
    }
}