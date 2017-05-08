package swak.undertow

import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.undertow.server.HttpServerExchange
import mu.KLogging
import swak.http.response.*

internal class ExchangeUpdater(private val exchange: HttpServerExchange) : KLogging(), SingleObserver<WritableResponse<Any>> {
    override fun onSubscribe(d: Disposable) {
    }

    override fun onSuccess(response: WritableResponse<Any>) {
        exchange.statusCode = response.status.code
        exchange.responseSender.send(response.writableBody)
        exchange.endExchange()
    }

    override fun onError(e: Throwable) {
        logger.error("unhandled error", e)
        exchange.statusCode = Code.INTERNAL_SERVER_ERROR.code
        exchange.endExchange()
    }
}