package io.swak.config.configurable

import io.swak.config.configurer.AroundConfigurer
import io.swak.handler.Handler
import io.swak.handler.HandlerBuilder
import io.swak.handler.interceptor.Around
import io.swak.handler.interceptor.errorHandler.ErrorHandler

internal interface ConfigurableAround<B> : AroundConfigurer, ConfigurableHandler<B> {
    val interceptHandlerBuilder: Around.Builder<B>
    val innerHandlerBuilder: HandlerBuilder<B>

    override fun handleError(errorHandler: ErrorHandler) {
        interceptHandlerBuilder.errorHandlersBuilder.errorHandlers.add(errorHandler)
    }

    override fun build(): Handler<B> =
            if (interceptHandlerBuilder.hasBehaviour()) interceptHandlerBuilder.build(innerHandlerBuilder.build())
            else innerHandlerBuilder.build()
}