package io.swak.config.configurer

import io.swak.config.configurable.SimpleConfigurableAround
import io.swak.handler.interceptor.errorHandler.ErrorHandler
import io.swak.http.Response

class SimpleAroundConfigurer internal constructor(
        private val configurable: SimpleConfigurableAround
) : AroundConfigurer by configurable {
    inline fun <reified E : Throwable> handleError(noinline errorHandler: (E) -> Response) {
        handleError(ErrorHandler.of(errorHandler))
    }
}