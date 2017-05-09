package swak.undertow

import io.reactivex.Single
import io.undertow.server.HttpServerExchange
import swak.http.Headers
import swak.http.MutableHeaders
import swak.http.request.BasicRequest
import swak.http.request.Method
import java.util.*

internal class UndertowBasicRequest(private val exchange: HttpServerExchange) : BasicRequest {
    override val headers: Headers by lazy {
        MutableHeaders(exchange.requestHeaders
                .map { it.headerName.toString() to ArrayList(it) }
                .toMap()
                .toMutableMap())
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
                    { _, error -> it.onError(error) },
                    charset(exchange.requestCharset))
        }
    }

    override val queryParam: Map<String, List<String>> by lazy {
        exchange.queryParameters.map { entry -> entry.key to ArrayList(entry.value) }
                .toMap()
    }

}