package io.neyb.swak

import io.neyb.swak.http.BasicRequest
import io.neyb.swak.http.Request
import io.neyb.swak.route.Route
import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange

internal class RouteAdapterHttpHandler(private val mainRoute: Route<String>) : HttpHandler {
    override fun handleRequest(exchange: HttpServerExchange) {
        exchange.dispatch(Runnable {
            mainRoute.handle(Request(BasicRequest(exchange), { it }))
                    .subscribe(ExchangeUpdater(exchange))
        })
    }
}