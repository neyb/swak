package swak.interceptor.errorHandler

import swak.body.writer.provider.request.BodyWriterChooser
import swak.http.request.Request
import swak.http.response.*

class ErrorRecoverer<out ErrBody : Any>(
        private val errorHandler: ErrorHandler<ErrBody>,
        private val writerChooser: BodyWriterChooser<ErrBody>
) {
    fun recoverFrom(request: Request<*>, error: Throwable): WritableResponse<ErrBody>? {
        return errorHandler.onError(error)
                ?.let { response ->
                    SimpleWritableResponse(
                            response,
                            writerChooser.`for`(response, request))
                }
    }
}