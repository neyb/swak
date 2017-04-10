package swak.interceptor.errorHandler

import swak.http.response.SimpleResponse

internal class SpecificErrorHandler<in E : Throwable, out Body : Any>(
        private val handledErrorType: Class<E>,
        private val errorHandler: (E) -> SimpleResponse<Body>
) : ErrorHandler<Body> {
    override fun onError(error: Throwable) =
            if (handledErrorType.isAssignableFrom(error.javaClass))
                @Suppress("UNCHECKED_CAST")
                errorHandler(error as E)
            else null

    override fun toString() = "handle ${handledErrorType.simpleName}"
}