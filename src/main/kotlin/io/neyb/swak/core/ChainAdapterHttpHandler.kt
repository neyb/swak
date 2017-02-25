package io.neyb.swak.core

import io.neyb.swak.core.chain.Chain
import io.neyb.swak.core.http.Request
import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange

class ChainAdapterHttpHandler(private val chain: Chain) : HttpHandler {

    override fun handleRequest(exchange: HttpServerExchange) {
        exchange.dispatch(Runnable {
            chain.handle(Request(exchange))
                    .subscribe {
                        exchange.statusCode = it.status.code
                        exchange.responseSender.send(it.body.toString())
                    }
        })
    }
}