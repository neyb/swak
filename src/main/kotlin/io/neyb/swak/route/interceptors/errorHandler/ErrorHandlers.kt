package io.neyb.swak.route.interceptors.errorHandler

import io.neyb.swak.http.Response

class ErrorHandlers : ErrorHandler {
    private val errorHandlers = mutableListOf<ErrorHandler>()

    fun add(errorHandler: ErrorHandler) {
        errorHandlers.add(errorHandler)
    }

    override fun onError(error: Throwable): Response? =
            errorHandlers.asSequence()
                    .mapNotNull { it.onError(error) }
                    .firstOrNull()
}