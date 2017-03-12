package swak.config.configurable

import swak.config.configurer.AroundConfigurer
import swak.handler.*
import swak.interceptor.errorHandler.ErrorHandler

internal interface ConfigurableAround<B> : AroundConfigurer, ConfigurableHandler<B> {
    val interceptHandlerBuilder: Around.Builder<B>

    override fun handleError(errorHandler: ErrorHandler) {
        interceptHandlerBuilder.errorHandlers.errorHandlers.add(errorHandler)
    }

    override fun build(): Handler<B> = interceptHandlerBuilder.build()
}