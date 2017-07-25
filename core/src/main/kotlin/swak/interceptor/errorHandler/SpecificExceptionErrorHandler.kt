package swak.interceptor.errorHandler

import swak.http.response.*
import kotlin.reflect.KClass

internal class SpecificExceptionErrorHandler<in E : Throwable, out Body>(
        private val handledErrorType: KClass<E>,
        private val errorHandler: (E) -> Response<Body>
) : ErrorHandler<Body> {
    override fun onError(error: Throwable) =
            if (handledErrorType.isInstance(error))
                @Suppress("UNCHECKED_CAST")
                errorHandler(error as E).toErrorResponse()
            else null

    override fun toString() = "handle ${handledErrorType.simpleName}"

    private fun <Body> Response<Body>.toErrorResponse() = ErrorResponse(status, headers, body)
}