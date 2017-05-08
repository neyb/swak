package swak.config.configurer

import swak.body.reader.provider.type.BodyReaderChooserProvider
import swak.body.writer.provider.type.BodyWriterChooserProvider

interface HandlerConfigurer {
    fun addContentReaderProvider(bodyReaderChooserProvider: BodyReaderChooserProvider)
    fun addContentWriterProvider(bodyWriterChooserProvider: BodyWriterChooserProvider)
}