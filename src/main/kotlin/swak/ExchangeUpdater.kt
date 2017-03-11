package swak

import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.undertow.server.HttpServerExchange
import mu.KLogging
import swak.http.Code
import swak.http.Response

internal class ExchangeUpdater(private val exchange: HttpServerExchange) : KLogging(), SingleObserver<Response> {
    override fun onSubscribe(d: Disposable) {
    }

    override fun onSuccess(response: Response) {
        exchange.statusCode = response.status.code
        exchange.responseSender.send(response.body?.toString() ?: "")
        exchange.endExchange()
    }

    override fun onError(e: Throwable) {
        logger.error("unhandled error", e)
        exchange.statusCode = Code.INTERNAL_SERVER_ERROR.code
        exchange.endExchange()
    }
}