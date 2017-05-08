package swak.config.configurable

import swak.config.configurer.AroundConfigurer
import swak.handler.*
import swak.interceptor.errorHandler.ErrorHandler

internal interface ConfigurableAround<IB, out OB:Any> : AroundConfigurer, ConfigurableHandler<IB> {
    val interceptHandlerBuilder: Around.Builder<IB>
    override fun <T : Any> handleError(target: Class<T>, errorHandler: ErrorHandler<T>) {
        interceptHandlerBuilder.errorRecoverers.add(target, errorHandler)
    }

    override fun build(): Handler<IB> = interceptHandlerBuilder.build(bodyWriterTypeProviders)
}