package swak.config.configurer

import swak.config.configurable.SimpleConfigurableAround
import swak.http.Response
import swak.interceptor.errorHandler.ErrorHandler

class SimpleAroundConfigurer internal constructor(
        private val configurable: SimpleConfigurableAround
) : AroundConfigurer by configurable {
    inline fun <reified E : Throwable> handleError(noinline errorHandler: (E) -> Response) {
        handleError(ErrorHandler.of(errorHandler))
    }
}