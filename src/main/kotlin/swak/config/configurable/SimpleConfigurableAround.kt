package swak.config.configurable

import swak.handler.Around
import swak.reader.provider.type.BodyReaderTypeProviders

internal class SimpleConfigurableAround(
        override val parent: ConfigurableHandler<*>? = null,
        override val localPath: String? = null
) : ConfigurableAround<String> {
    override val bodyReaderTypeProviders = BodyReaderTypeProviders(parent?.bodyReaderTypeProviders)
    override val interceptHandlerBuilder = Around.Builder<String>()
}