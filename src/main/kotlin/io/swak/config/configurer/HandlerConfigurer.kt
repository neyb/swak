package io.swak.config.configurer

import io.swak.handler.converter.reader.provider.type.BodyReaderTypeProvider

interface HandlerConfigurer {
    fun addContentReaderProvider(bodyReaderTypeProvider: BodyReaderTypeProvider)
}