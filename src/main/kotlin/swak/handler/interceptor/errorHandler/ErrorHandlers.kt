package swak.handler.interceptor.errorHandler

import swak.http.Response

internal class ErrorHandlers(
        private val errorHandlers: List<ErrorHandler>
) : ErrorHandler {

    override fun onError(error: Throwable): Response? =
            errorHandlers.asSequence()
                    .mapNotNull { it.onError(error) }
                    .firstOrNull()

    class Builder {
        val errorHandlers = mutableListOf<ErrorHandler>()
        fun  hasBehaviour() = !errorHandlers.isEmpty()
        fun build() = ErrorHandlers(errorHandlers.toList())
    }
}