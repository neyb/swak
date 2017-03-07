package io.neyb.swak.handler.interceptor.errorHandler

import io.neyb.swak.http.Response

class ErrorHandlers(
        private val errorHandlers: List<ErrorHandler>
) : ErrorHandler {

    override fun onError(error: Throwable): Response? =
            errorHandlers.asSequence()
                    .mapNotNull { it.onError(error) }
                    .firstOrNull()

    class Builder {
        val errorHandlers = mutableListOf<ErrorHandler>()
        fun build() = ErrorHandlers(errorHandlers.toList())
    }
}