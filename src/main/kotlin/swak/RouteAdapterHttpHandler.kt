package swak

import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange
import swak.handler.Handler
import swak.http.request.UndertowBasicRequest
import swak.http.request.UpdatableRequest
import swak.http.requestContext.UpdatableRequestContext
import swak.reader.TextReader

internal class RouteAdapterHttpHandler(private val mainHandler: Handler<String>) : HttpHandler {
    override fun handleRequest(exchange: HttpServerExchange) {
        exchange.dispatch(Runnable {
            mainHandler.handle(UpdatableRequestContext(UpdatableRequest(UndertowBasicRequest(exchange), TextReader)))
                    .map { it.response }
                    .subscribe(ExchangeUpdater(exchange))
        })
    }
}