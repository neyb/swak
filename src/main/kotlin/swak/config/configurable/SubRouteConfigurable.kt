package swak.config.configurable

import swak.handler.Around
import swak.handler.router.Router
import swak.reader.provider.type.BodyReaderTypeProviders

internal class SubRouteConfigurable(
        override val parent: ConfigurableHandler<*>? = null,
        override val localPath: String? = null
) : ConfigurableAround<String>, ConfigurableRouter{
    override val bodyReaderTypeProviders = BodyReaderTypeProviders(parent?.bodyReaderTypeProviders)
    override val routerHandlerBuilder = Router.Builder()
    override val interceptHandlerBuilder = Around.Builder<String>().apply {
        innerHandler = routerHandlerBuilder
    }
    override fun build() = super<ConfigurableAround>.build()
}