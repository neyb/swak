package io.neyb.swak.chain.route.interceptor.errorHandlers

import io.neyb.swak.chain.route.NoRouteFound
import io.neyb.swak.chain.route.interceptor.Interceptor
import io.neyb.swak.http.Response
import io.neyb.swak.http.Status
import mu.*

object NoRouteInterceptor : Interceptor, KLoggable {
    override val logger = logger()

    override fun onError(error: Throwable) =
            if (error is NoRouteFound) {
                logger.error { error.message }
                Response(status = Status.NOT_FOUND)
            } else null
}