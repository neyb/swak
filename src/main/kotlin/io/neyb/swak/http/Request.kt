package io.neyb.swak.http

import io.reactivex.Single
import io.undertow.server.HttpServerExchange

class Request(private val exchange: HttpServerExchange,
              val pathParams:Map<String,String> = emptyMap()
              ) {
    val requestPath: String
        get() = exchange.requestPath
    val method: Method
        get() = Method.valueOf(exchange.requestMethod.toString())
    val body: Single<String>
        get() = Single.create<String> {
            exchange.requestReceiver.receiveFullString(
                    { exchange, stringValue -> it.onSuccess(stringValue) },
                    charset(exchange.requestCharset))
        }

    fun withPathParam(pathParams: Map<String, String>) =
            Request(exchange, pathParams)
}
