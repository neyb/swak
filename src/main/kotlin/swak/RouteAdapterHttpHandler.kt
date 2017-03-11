package swak

import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange
import swak.handler.Handler
import swak.http.UndertowBasicRequest
import swak.http.UpdatableRequest
import swak.reader.TextReader

internal class RouteAdapterHttpHandler(private val mainHandler: Handler<String>) : HttpHandler {
    override fun handleRequest(exchange: HttpServerExchange) {
        exchange.dispatch(Runnable {
            mainHandler.handle(UpdatableRequest(UndertowBasicRequest(exchange), TextReader))
                    .subscribe(ExchangeUpdater(exchange))
        })
    }
}