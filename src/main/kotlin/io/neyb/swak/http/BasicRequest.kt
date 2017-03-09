package io.neyb.swak.http

import io.reactivex.Single
import io.undertow.server.HttpServerExchange
import java.util.*

internal class BasicRequest(private val exchange: HttpServerExchange) {
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
                    { _, stringValue -> it.onSuccess(stringValue) },
                    charset(exchange.requestCharset))
        }
    }

}