package io.neyb.swak

import io.neyb.swak.handler.Handler
import io.neyb.swak.handler.converter.reader.TextReader
import io.neyb.swak.http.*
import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange

internal class RouteAdapterHttpHandler(private val mainHandler: Handler<String>) : HttpHandler {
    override fun handleRequest(exchange: HttpServerExchange) {
        exchange.dispatch(Runnable {
            mainHandler.handle(UpdatableRequest(BasicRequest(exchange), TextReader, NoPathParamExtractor()))
                    .subscribe(ExchangeUpdater(exchange))
        })
    }
}