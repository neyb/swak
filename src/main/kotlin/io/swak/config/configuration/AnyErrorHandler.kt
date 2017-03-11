package io.swak.config.configuration

import io.swak.handler.interceptor.errorHandler.ErrorHandler
import io.swak.http.Code
import io.swak.http.Response
import mu.KLogging

internal object AnyErrorHandler : KLogging(), ErrorHandler {
    override fun onError(error: Throwable): Response? {
        logger.error(error) { "an unhandled error occured" }
        return Response(status = Code.INTERNAL_SERVER_ERROR)
    }
}