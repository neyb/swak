package io.neyb.swak.handler.config.configurable

import io.neyb.swak.handler.config.builder.ConfigurableHandler
import io.neyb.swak.handler.config.configuration.HandlerConfiguration
import io.neyb.swak.handler.interceptor.errorHandler.ErrorHandler
import io.neyb.swak.http.Response

open class HandlerConfigurer(
        private val configurableHandler: ConfigurableHandler
) {
    inline fun <reified E : Throwable> addErrorHandler(noinline errorHandler: (E) -> Response) {
        addErrorHandler(ErrorHandler.of(errorHandler))
    }

    fun addErrorHandler(errorHandler: ErrorHandler) {
        configurableHandler.interceptHandlerBuilder.errorHandlersBuilder.errorHandlers.add(errorHandler)
    }

}