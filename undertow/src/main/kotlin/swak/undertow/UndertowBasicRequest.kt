package swak.undertow

import io.undertow.server.HttpServerExchange
import swak.http.*
import swak.http.request.*
import java.util.*
import kotlin.coroutines.experimental.suspendCoroutine

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

    //TODO make it lazy
    override suspend fun body(): String = suspendCoroutine { cont ->
        exchange.requestReceiver.receiveFullString(
                { _, stringValue -> cont.resume(stringValue) },
                { _, error -> cont.resumeWithException(error) },
                charset(exchange.requestCharset))
    }

    override val queryParam: Map<String, List<String>> by lazy {
        exchange.queryParameters.map { entry -> entry.key to ArrayList(entry.value) }
                .toMap()
    }

}