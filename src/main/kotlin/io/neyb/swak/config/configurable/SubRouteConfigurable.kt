package io.neyb.swak.config.configurable

import io.neyb.swak.handler.converter.reader.provider.type.BodyReaderTypeProviders
import io.neyb.swak.handler.cross.Cross
import io.neyb.swak.handler.interceptor.InterceptableHandler

class SubRouteConfigurable(
        override val parent: ConfigurableHandler<*>? = null
) : ConfigurableIntercepter<String>, ConfigurableCross {
    override val bodyReaderTypeProviders = BodyReaderTypeProviders(parent?.bodyReaderTypeProviders)
    override val interceptHandlerBuilder = InterceptableHandler.Builder<String>()
    override val innerHandlerBuilder = Cross.Builder()
    override val crossHandlerBuilder get() = innerHandlerBuilder
    override fun build() = super<ConfigurableIntercepter>.build()
}