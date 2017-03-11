package swak.config.configuration

import mu.KLogging
import swak.handler.interceptor.errorHandler.ErrorHandler
import swak.http.Code
import swak.http.Response

internal object AnyErrorHandler : KLogging(), ErrorHandler {
    override fun onError(error: Throwable): Response? {
        logger.error(error) { "an unhandled error occured" }
        return Response(status = Code.INTERNAL_SERVER_ERROR)
    }
}