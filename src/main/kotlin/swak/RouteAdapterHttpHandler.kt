package swak

import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange
import swak.handler.Handler
import swak.reader.TextReader
import swak.http.*

internal class RouteAdapterHttpHandler(private val mainHandler: Handler<String>) : HttpHandler {
    override fun handleRequest(exchange: HttpServerExchange) {
        exchange.dispatch(Runnable {
            mainHandler.handle(UpdatableRequest(UndertowBasicRequest(exchange), TextReader, NoPathParamExtractor()))
                    .subscribe(ExchangeUpdater(exchange))
        })
    }
}