package io.neyb.swak

import io.neyb.swak.http.Response
import io.neyb.swak.http.Status
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.undertow.server.HttpServerExchange

class ExchangeUpdater(private val exchange: HttpServerExchange) : SingleObserver<Response> {
    override fun onSubscribe(d: Disposable) {
    }

    override fun onSuccess(response: Response) {
        exchange.statusCode = response.status.code
        exchange.responseSender.send(response.body.toString())
    }

    override fun onError(e: Throwable) {
        exchange.statusCode = Status.INTERNAL_ERROR.code
        exchange.endExchange()
    }
}