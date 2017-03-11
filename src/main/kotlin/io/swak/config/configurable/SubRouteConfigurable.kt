package io.swak.config.configurable

import io.swak.handler.converter.reader.provider.type.BodyReaderTypeProviders
import io.swak.handler.cross.Router
import io.swak.handler.interceptor.Around

internal class SubRouteConfigurable(
        override val parent: ConfigurableHandler<*>? = null,
        override val localPath: String? = null
) : ConfigurableAround<String>, ConfigurableRouter {
    override val bodyReaderTypeProviders = BodyReaderTypeProviders(parent?.bodyReaderTypeProviders)
    override val interceptHandlerBuilder = Around.Builder<String>()
    override val innerHandlerBuilder = Router.Builder()
    override val routerHandlerBuilder get() = innerHandlerBuilder
    override fun build() = super<ConfigurableAround>.build()
}