package io.neyb.swak.chain.route.interceptor.errorHandlers

import io.neyb.swak.chain.route.SeveralRouteFound
import io.neyb.swak.chain.route.interceptor.Interceptor
import io.neyb.swak.http.Response
import io.neyb.swak.http.Status
import mu.KLoggable

object SeveralRouteInterceptor : Interceptor, KLoggable {
    override val logger = logger()

    override fun onError(error: Throwable) =
            if (error is SeveralRouteFound) {
                logger.error { error.message }
                Response(status = Status.INTERNAL_ERROR)
            } else null
}