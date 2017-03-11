package swak.config.configurable

import swak.handler.HandlerBuilder
import swak.handler.converter.reader.provider.type.BodyReaderTypeProviders
import swak.handler.interceptor.Around
import kotlin.properties.Delegates

internal class SimpleConfigurableAround(
        override val parent: ConfigurableHandler<*>? = null,
        override val localPath: String? = null
) : ConfigurableAround<String> {
    override val bodyReaderTypeProviders = BodyReaderTypeProviders(parent?.bodyReaderTypeProviders)
    override var innerHandlerBuilder: HandlerBuilder<String> by Delegates.notNull()
    override val interceptHandlerBuilder = Around.Builder<String>()
}