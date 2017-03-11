package swak.config.configurable

import swak.config.configurer.AroundConfigurer
import swak.handler.Handler
import swak.handler.HandlerBuilder
import swak.handler.Around
import swak.interceptor.errorHandler.ErrorHandler

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