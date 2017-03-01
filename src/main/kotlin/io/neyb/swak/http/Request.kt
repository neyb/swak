package io.neyb.swak.http

import io.neyb.swak.chain.AdditionalData
import io.reactivex.Single
import io.undertow.server.HttpServerExchange
import java.util.*

class Request(private val exchange: HttpServerExchange,
              val pathParams: Map<String, String> = emptyMap()
) {
    val headers: Headers by lazy {
        Headers(exchange.requestHeaders
                .map { it.headerName.toString() to ArrayList(it) }
                .toMap())
    }

    val path: String by lazy {
        exchange.requestPath
    }

    val method: Method by lazy {
        Method.valueOf(exchange.requestMethod.toString())
    }

    val body: Single<String> by lazy {
        Single.create<String> {
            exchange.requestReceiver.receiveFullString(
                    { exchange, stringValue -> it.onSuccess(stringValue) },
                    charset(exchange.requestCharset))
        }
    }

    val additionalData: AdditionalData = AdditionalData()

    fun withPathParam(pathParams: Map<String, String>) =
            Request(exchange, pathParams)
}
