package io.neyb.swak

import io.neyb.swak.handler.RequestHandler
import io.neyb.swak.http.BasicRequest
import io.neyb.swak.http.Request
import io.neyb.swak.reader.TextReader
import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange

internal class RouteAdapterHttpHandler(private val mainHandler: RequestHandler<String>) : HttpHandler {
    override fun handleRequest(exchange: HttpServerExchange) {
        exchange.dispatch(Runnable {
            mainHandler.handle(Request(BasicRequest(exchange), TextReader))
                    .subscribe(ExchangeUpdater(exchange))
        })
    }
}