package swak.config.configurer

import swak.reader.provider.type.BodyReaderTypeProvider

interface HandlerConfigurer {
    fun addContentReaderProvider(bodyReaderTypeProvider: BodyReaderTypeProvider)
}