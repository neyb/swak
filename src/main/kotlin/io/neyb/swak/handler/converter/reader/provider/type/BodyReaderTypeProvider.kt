package io.neyb.swak.handler.converter.reader.provider.type

import io.neyb.swak.handler.converter.reader.provider.request.BodyReaderRequestProvider

interface BodyReaderTypeProvider {
    fun <B> forClass(target: Class<B>): BodyReaderRequestProvider<B>?
}