package swak.undertow

import io.undertow.server.*
import swak.server.RootReqHandler

internal class RouteAdapterHttpHandler(private val rootReqHandler: RootReqHandler) : HttpHandler {
    override fun handleRequest(exchange: HttpServerExchange) {
        exchange.dispatch(Runnable {
            rootReqHandler
                    .handle(UndertowBasicRequest(exchange))
                    .subscribe(ExchangeUpdater(exchange))
        })
    }
}