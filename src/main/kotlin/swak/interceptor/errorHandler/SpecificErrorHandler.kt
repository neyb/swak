package swak.interceptor.errorHandler

import swak.http.Response

internal class SpecificErrorHandler<in E:Throwable>(
        private val handledErrorType: Class<E>,
        private val errorHandler: (E)-> Response
) : ErrorHandler {
    override fun onError(error: Throwable) =
            if (handledErrorType.isAssignableFrom(error.javaClass))
                @Suppress("UNCHECKED_CAST")
                errorHandler(error as E)
            else null

    override fun toString() = "handle ${handledErrorType.simpleName}"
}