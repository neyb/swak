package swak.config.configurer

import swak.config.configurable.SimpleConfigurableAround
import swak.http.response.ErrorResponse
import swak.interceptor.errorHandler.ErrorHandler

class SimpleAroundConfigurer<OB> internal constructor(
        private val configurable: SimpleConfigurableAround<OB>
) : AroundConfigurer by configurable {
    inline fun <reified E : Throwable, reified OB> handleError(noinline errorHandler: (E) -> ErrorResponse<OB>) {
        handleError(OB::class.java, ErrorHandler.of(errorHandler))
    }
}