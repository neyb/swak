package io.neyb.swak.config.configurer

import io.neyb.swak.config.configurable.SimpleConfigurableIntercepter
import io.neyb.swak.handler.interceptor.errorHandler.ErrorHandler
import io.neyb.swak.http.Response

class SimpleIntercepterConfigurer(
        private val configurable: SimpleConfigurableIntercepter
) : IntercepterConfigurer by configurable {
    inline fun <reified E : Throwable> handleError(noinline errorHandler: (E) -> Response) {
        handleError(ErrorHandler.of(errorHandler))
    }
}