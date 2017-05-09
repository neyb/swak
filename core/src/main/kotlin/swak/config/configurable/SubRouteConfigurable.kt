package swak.config.configurable

import swak.body.reader.provider.type.BodyReaderChooserProviders
import swak.body.writer.provider.type.BodyWriterChooserProviders
import swak.handler.Around
import swak.handler.router.Router

internal class SubRouteConfigurable(
        override val parent: ConfigurableHandler<*>? = null,
        override val localPath: String? = null
) : ConfigurableAround<String, String>, ConfigurableRouter{
    override val bodyReaderTypeProviders = BodyReaderChooserProviders(parent?.bodyReaderTypeProviders)
    override val bodyWriterTypeProviders = BodyWriterChooserProviders(parent?.bodyWriterTypeProviders)
    override val routerHandlerBuilder = Router.Builder()
    override val interceptHandlerBuilder = Around.Builder<String>().apply {
        innerHandler = routerHandlerBuilder
        bodyWriterTypeProviders
    }
    override fun build() = super<ConfigurableAround>.build()
}