package swak.interceptor.errorHandler

import swak.http.response.Response

internal class ErrorHandlers(
        private val errorHandlers: List<ErrorHandler>
) : ErrorHandler {

    override fun onError(error: Throwable): Response? =
            errorHandlers.asSequence()
                    .mapNotNull { it.onError(error) }
                    .firstOrNull()

    override fun toString() = errorHandlers.toString()


    class Builder {
        val errorHandlers = mutableListOf<ErrorHandler>()
        fun  hasBehaviour() = !errorHandlers.isEmpty()
        fun build() = ErrorHandlers(errorHandlers.toList())
    }
}