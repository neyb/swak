package io.neyb.swak.config.builder

import io.neyb.swak.handler.HandlerBuilder
import io.neyb.swak.handler.converter.reader.provider.type.BodyReaderTypeProviders
import io.neyb.swak.handler.interceptor.InterceptableHandler
import io.neyb.swak.handler.interceptor.errorHandler.ErrorHandler

abstract class ConfigurableHandler(
        protected val parent: ConfigurableHandler? = null
) : HandlerBuilder<String> {
    protected abstract val innerHandlerBuilder: HandlerBuilder<String>

    val interceptHandlerBuilder = InterceptableHandler.Builder<String>()

    val bodyReaderTypeProviders: BodyReaderTypeProviders by lazy {
        BodyReaderTypeProviders(parent?.bodyReaderTypeProviders)
    }

    fun addErrorhandler(errorHandler: ErrorHandler) {
        interceptHandlerBuilder.errorHandlersBuilder.errorHandlers.add(errorHandler)
    }

    override fun build() = interceptHandlerBuilder.build(innerHandlerBuilder.build())
}