package swak.handler.interceptor.errorHandler

import swak.http.Response

interface ErrorHandler {
    companion object {
        inline fun <reified E:Throwable> of(noinline errorHandler: (E)-> Response): ErrorHandler =
                ErrorHandler.Companion.of(E::class.java, errorHandler)

        fun <E : Throwable> of(errorClass: Class<E>, errorHandler: (E)-> Response): ErrorHandler =
                SpecificErrorHandler(errorClass, errorHandler)
    }

    fun onError(error: Throwable): Response?
}