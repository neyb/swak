package swak.http.request

import io.reactivex.Single
import io.undertow.server.HttpServerExchange
import swak.http.*
import java.util.*

internal class UndertowBasicRequest(private val exchange: HttpServerExchange): BasicRequest {
    override val headers: Headers by lazy {
        Headers(exchange.requestHeaders
                .map { it.headerName.toString() to ArrayList(it) }
                .toMap())
    }

    override val path: String by lazy {
        exchange.requestPath
    }

    override val method: Method by lazy {
        Method.valueOf(exchange.requestMethod.toString())
    }

    override val body: Single<String> by lazy {
        Single.create<String> {
            exchange.requestReceiver.receiveFullString(
                    { _, stringValue -> it.onSuccess(stringValue) },
                    charset(exchange.requestCharset))
        }
    }

}