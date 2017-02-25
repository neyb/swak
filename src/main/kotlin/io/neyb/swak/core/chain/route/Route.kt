package io.neyb.swak.core.chain.route

import io.neyb.swak.core.http.Request
import io.neyb.swak.core.http.Response
import rx.Observable

interface Route {
    companion object factory

    fun accept(exchange: Request): Boolean
    fun handle(exchange: Request): Observable<Response>
}

fun Route.factory.get(path: String, handler: (Request) -> Observable<Response>) =
        object : Route {
            override fun accept(exchange: Request) =
                    exchange.requestPath == path

            override fun handle(exchange: Request) = handler(exchange)

        }