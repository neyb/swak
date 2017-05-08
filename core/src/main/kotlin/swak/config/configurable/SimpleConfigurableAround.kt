package swak.config.configurable

import swak.body.reader.provider.type.BodyReaderChooserProviders
import swak.body.writer.provider.type.BodyWriterChooserProviders
import swak.handler.Around

internal class SimpleConfigurableAround(
        override val parent: ConfigurableHandler<*>? = null,
        override val localPath: String? = null
) : ConfigurableAround<String, String> {
    override val bodyReaderTypeProviders = BodyReaderChooserProviders(parent?.bodyReaderTypeProviders)
    override val bodyWriterTypeProviders = BodyWriterChooserProviders(parent?.bodyWriterTypeProviders)
    override val interceptHandlerBuilder = Around.Builder<String>()
}