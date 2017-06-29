package swak.interceptor.errorHandler

import swak.http.response.ErrorResponse
import kotlin.reflect.KClass

interface ErrorHandler<out OB> {
    companion object {
        inline fun <reified E : Throwable, OB> of(noinline errorHandler: (E) -> ErrorResponse<OB>): ErrorHandler<OB> =
                of(E::class, errorHandler)

        fun <E : Throwable, OB> of(errorClass: KClass<E>, errorHandler: (E) -> ErrorResponse<OB>): ErrorHandler<OB> =
                SpecificExceptionErrorHandler(errorClass, errorHandler)
    }

    fun onError(error: Throwable): ErrorResponse<OB>?
}