package io.neyb.swak.chain.interceptor.errorHandler

import io.neyb.swak.chain.route.SeveralRouteFound
import io.neyb.swak.http.Response
import io.neyb.swak.http.Code
import mu.KLoggable

object SeveralRouteInterceptor : KLoggable, ErrorHandler {
    override val logger = logger()

    override fun onError(error: Throwable) =
            if (error is SeveralRouteFound) {
                logger.error { error.message }
                Response(status = Code.INTERNAL_SERVER_ERROR)
            } else null
}