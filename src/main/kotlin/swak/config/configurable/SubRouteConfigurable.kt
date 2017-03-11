package swak.config.configurable

import swak.reader.provider.type.BodyReaderTypeProviders
import swak.handler.router.Router
import swak.handler.Around

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