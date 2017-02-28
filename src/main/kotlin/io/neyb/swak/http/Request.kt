package io.neyb.swak.http

import io.neyb.swak.chain.AdditionalData
import io.reactivex.Single
import io.undertow.server.HttpServerExchange

class Request(private val exchange: HttpServerExchange,
              val pathParams:Map<String,String> = emptyMap()
              ) {
    val path: String
        get() = exchange.requestPath

    val method: Method
        get() = Method.valueOf(exchange.requestMethod.toString())

    val body: Single<String>
        get() = Single.create<String> {
            exchange.requestReceiver.receiveFullString(
                    { exchange, stringValue -> it.onSuccess(stringValue) },
                    charset(exchange.requestCharset))
        }

    val additionalData:AdditionalData = AdditionalData()

    fun withPathParam(pathParams: Map<String, String>) =
            Request(exchange, pathParams)
}
