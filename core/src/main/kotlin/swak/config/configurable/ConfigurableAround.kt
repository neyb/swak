package swak.config.configurable

import swak.config.configurer.AroundConfigurer
import swak.handler.Around
import swak.handler.Handler
import swak.interceptor.errorHandler.ErrorHandler

internal interface ConfigurableAround<IB, OB:Any> : AroundConfigurer, ConfigurableHandler<IB, OB> {
    val interceptHandlerBuilder: Around.Builder<IB, OB>
    override fun <T : Any> handleError(target: Class<T>, errorHandler: ErrorHandler<T>) {
        interceptHandlerBuilder.errorRecoverers.add(target, errorHandler)
    }

    override fun build(): Handler<IB> = interceptHandlerBuilder.build(bodyWriterTypeProviders)
}