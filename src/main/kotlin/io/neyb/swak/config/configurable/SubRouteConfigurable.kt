package io.neyb.swak.config.configurable

import io.neyb.swak.handler.converter.reader.provider.type.BodyReaderTypeProviders
import io.neyb.swak.handler.cross.Router
import io.neyb.swak.handler.interceptor.InterceptableHandler

class SubRouteConfigurable(
        override val parent: ConfigurableHandler<*>? = null,
        override val localPath: String? = null
) : ConfigurableIntercepter<String>, ConfigurableRouter {
    override val bodyReaderTypeProviders = BodyReaderTypeProviders(parent?.bodyReaderTypeProviders)
    override val interceptHandlerBuilder = InterceptableHandler.Builder<String>()
    override val innerHandlerBuilder = Router.Builder()
    override val routerHandlerBuilder get() = innerHandlerBuilder
    override fun build() = super<ConfigurableIntercepter>.build()
}