package swak.undertow

import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange
import swak.body.reader.StringReader
import swak.handler.Handler
import swak.http.request.UpdatableRequest
import swak.http.request.context.UpdatableRequestContext

internal class RouteAdapterHttpHandler(private val mainHandler: Handler<String>) : HttpHandler {
    override fun handleRequest(exchange: HttpServerExchange) {
        exchange.dispatch(Runnable {
            mainHandler.handle(UpdatableRequestContext(UpdatableRequest(UndertowBasicRequest(exchange), StringReader)))
                    .map { it.response }
                    .subscribe(ExchangeUpdater(exchange))
        })
    }
}