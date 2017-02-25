package io.neyb.swak.core.http

import io.undertow.server.HttpServerExchange

class Request(private val exchange: HttpServerExchange) {
    val requestPath: String
        get() = exchange.requestPath

}