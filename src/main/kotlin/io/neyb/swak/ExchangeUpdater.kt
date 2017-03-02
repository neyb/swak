package io.neyb.swak

import io.neyb.swak.http.Code
import io.neyb.swak.http.Response
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.undertow.server.HttpServerExchange

internal class ExchangeUpdater(private val exchange: HttpServerExchange) : SingleObserver<Response> {
    override fun onSubscribe(d: Disposable) {
    }

    override fun onSuccess(response: Response) {
        exchange.statusCode = response.status.code
        exchange.responseSender.send(response.body?.toString() ?: "")
        exchange.endExchange()
    }

    override fun onError(e: Throwable) {
        exchange.statusCode = Code.INTERNAL_SERVER_ERROR.code
        exchange.endExchange()
    }
}