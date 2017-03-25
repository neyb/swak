package swak.config.configurable

import swak.config.configurer.AroundConfigurer
import swak.handler.Around
import swak.handler.Handler
import swak.interceptor.errorHandler.ErrorHandler

internal interface ConfigurableAround<Body> : AroundConfigurer, ConfigurableHandler<Body> {
    val interceptHandlerBuilder: Around.Builder<Body>

    override fun handleError(errorHandler: ErrorHandler) {
        interceptHandlerBuilder.errorHandlers.errorHandlers.add(errorHandler)
    }

    override fun build(): Handler<Body> = interceptHandlerBuilder.build()
}