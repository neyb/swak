package io.swak

import io.swak.handler.Handler
import io.swak.handler.converter.reader.TextReader
import io.swak.http.*
import io.swak.http.BasicRequest
import io.swak.http.UpdatableRequest
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