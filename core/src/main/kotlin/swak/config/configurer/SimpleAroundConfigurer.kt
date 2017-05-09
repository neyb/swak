package swak.config.configurer

import swak.config.configurable.SimpleConfigurableAround
import swak.http.response.SimpleResponse
import swak.interceptor.errorHandler.ErrorHandler

class SimpleAroundConfigurer internal constructor(
        private val configurable: SimpleConfigurableAround
) : AroundConfigurer by configurable {
    inline fun <reified E : Throwable, reified OB : Any> handleError(noinline errorHandler: (E) -> SimpleResponse<OB>) {
        handleError(OB::class.java, ErrorHandler.of(errorHandler))
    }
}