package swak.undertow

import io.undertow.server.*
import kotlinx.coroutines.experimental.*
import mu.KLogging
import swak.http.response.*
import swak.server.RootReqHandler
import java.lang.*

internal class RouteAdapterHttpHandler(private val rootReqHandler: RootReqHandler) : KLogging(), HttpHandler {
    override fun handleRequest(exchange: HttpServerExchange) {
        exchange.dispatch(Runnable { launch(CommonPool) { exchange.answer(getResponse(exchange)) } })
    }

    private suspend fun getResponse(exchange: HttpServerExchange): WritableResponse<*> =
            try {
                rootReqHandler.handle(UndertowBasicRequest(exchange))
            } catch (e: Exception) {
                logger.error("unhandled error", e)
                StringResponse(Code.INTERNAL_SERVER_ERROR)
            }

    private fun HttpServerExchange.answer(response: WritableResponse<*>) {
        statusCode = response.status.code
        if (!response.writableBody.isEmpty()) responseSender.send(response.writableBody)
        endExchange()
    }
}