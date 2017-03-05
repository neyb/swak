package io.neyb.swak.route.configuration

import io.neyb.swak.route.interceptors.errorHandler.ErrorHandler
import io.neyb.swak.http.Code
import io.neyb.swak.http.Response
import mu.KLogging

object AnyErrorHandler : KLogging(), ErrorHandler {
    override fun onError(error: Throwable): Response? {
        logger.error(error) { "an unhandled error occured" }
        return Response(status = Code.INTERNAL_SERVER_ERROR)
    }
}