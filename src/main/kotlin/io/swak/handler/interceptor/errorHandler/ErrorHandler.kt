package io.swak.handler.interceptor.errorHandler

import io.swak.http.Response

interface ErrorHandler {
    companion object {
        inline fun <reified E:Throwable> of(noinline errorHandler: (E)->Response): ErrorHandler =
                of(E::class.java, errorHandler)

        fun <E : Throwable> of(errorClass: Class<E>, errorHandler: (E)->Response):ErrorHandler =
                SpecificErrorHandler(errorClass, errorHandler)
    }

    fun onError(error: Throwable): Response?
}