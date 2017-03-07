package io.neyb.swak.config.configurer

import io.neyb.swak.handler.converter.reader.provider.type.BodyReaderTypeProvider

interface HandlerConfigurer {
    fun addContentReaderProvider(bodyReaderTypeProvider: BodyReaderTypeProvider)
}