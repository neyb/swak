package io.neyb.swak.config.configurable

import io.neyb.swak.config.configurer.IntercepterConfigurer
import io.neyb.swak.handler.Handler
import io.neyb.swak.handler.HandlerBuilder
import io.neyb.swak.handler.interceptor.InterceptableHandler
import io.neyb.swak.handler.interceptor.errorHandler.ErrorHandler

interface ConfigurableIntercepter<B> : IntercepterConfigurer, ConfigurableHandler<B> {
    val interceptHandlerBuilder : InterceptableHandler.Builder<B>
    val innerHandlerBuilder: HandlerBuilder<B>

    override fun handleError(errorHandler: ErrorHandler) {
        interceptHandlerBuilder.errorHandlersBuilder.errorHandlers.add(errorHandler)
    }

    override fun build(): Handler<B> = interceptHandlerBuilder.build(innerHandlerBuilder.build())

}