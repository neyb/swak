package io.neyb.swak.chain.interceptor.errorHandler

import io.neyb.swak.chain.route.NoRouteFound
import io.neyb.swak.http.Response
import io.neyb.swak.http.Code
import mu.*

object NoRouteInterceptor : KLoggable, ErrorHandler {
    override val logger = logger()

    override fun onError(error: Throwable) =
            if (error is NoRouteFound) {
                logger.error { error.message }
                Response(status = Code.NOT_FOUND)
            } else null
}