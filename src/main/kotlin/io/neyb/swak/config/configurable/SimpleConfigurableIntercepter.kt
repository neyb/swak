package io.neyb.swak.config.configurable

import io.neyb.swak.handler.HandlerBuilder
import io.neyb.swak.handler.converter.reader.provider.type.BodyReaderTypeProviders
import io.neyb.swak.handler.interceptor.InterceptableHandler
import kotlin.properties.Delegates

class SimpleConfigurableIntercepter(
        override val parent: ConfigurableHandler<*>? = null
) : ConfigurableIntercepter<String> {
    override val bodyReaderTypeProviders = BodyReaderTypeProviders(parent?.bodyReaderTypeProviders)
    override var innerHandlerBuilder: HandlerBuilder<String> by Delegates.notNull()
    override val interceptHandlerBuilder = InterceptableHandler.Builder<String>()
}