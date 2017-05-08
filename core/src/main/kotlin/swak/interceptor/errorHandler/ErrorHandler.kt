package swak.interceptor.errorHandler

import swak.http.response.NotWritableResponse
import swak.http.response.SimpleResponse

interface ErrorHandler<out OB:Any> {
    companion object {
        inline fun <reified E:Throwable, OB : Any> of(noinline errorHandler: (E)-> SimpleResponse<OB>): ErrorHandler<OB> =
                of(E::class.java, errorHandler)

        fun <E : Throwable, OB : Any> of(errorClass: Class<E>, errorHandler: (E)-> SimpleResponse<OB>): ErrorHandler<OB> =
                SpecificErrorHandler(errorClass, errorHandler)
    }

    fun onError(error: Throwable): NotWritableResponse<OB>?
}