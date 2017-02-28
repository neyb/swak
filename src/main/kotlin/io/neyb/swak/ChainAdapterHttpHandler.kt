package io.neyb.swak

import io.neyb.swak.chain.Chain
import io.neyb.swak.http.Request
import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange

class ChainAdapterHttpHandler(private val chain: Chain) : HttpHandler {

    override fun handleRequest(exchange: HttpServerExchange) {
        exchange.dispatch(Runnable {
            chain.handle(Request(exchange))
                    .subscribe(ExchangeUpdater(exchange))})
    }
}