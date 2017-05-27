package swak.interceptor.errorHandler

import swak.http.response.*
import kotlin.reflect.KClass

interface ErrorHandler<out OB:Any> {
    companion object {
        inline fun <reified E:Throwable, OB : Any> of(noinline errorHandler: (E)-> SimpleResponse<OB>): ErrorHandler<OB> =
                of(E::class, errorHandler)

        fun <E : Throwable, OB : Any> of(errorClass: KClass<E>, errorHandler: (E)-> SimpleResponse<OB>): ErrorHandler<OB> =
                SpecificExceptionErrorHandler(errorClass, errorHandler)
    }

    fun onError(error: Throwable): NotWritableResponse<OB>?
}