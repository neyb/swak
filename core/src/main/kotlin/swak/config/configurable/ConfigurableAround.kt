package swak.config.configurable

import swak.config.configurer.AroundConfigurer
import swak.handler.*
import swak.interceptor.errorHandler.ErrorHandler

internal interface ConfigurableAround<IB, OB> : AroundConfigurer, ConfigurableHandler<IB, OB> {
    val interceptHandlerBuilder: Around.Builder<IB, OB>
    override fun <T> handleError(target: Class<T>, errorHandler: ErrorHandler<T>) {
        interceptHandlerBuilder.errorRecoverers.add(target, errorHandler)
    }

    override fun build(): Handler<IB, OB> = interceptHandlerBuilder.build(bodyWriterTypeProviders)
}