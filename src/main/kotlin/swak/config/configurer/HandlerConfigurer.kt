package swak.config.configurer

import swak.handler.converter.reader.provider.type.BodyReaderTypeProvider

interface HandlerConfigurer {
    fun addContentReaderProvider(bodyReaderTypeProvider: BodyReaderTypeProvider)
}