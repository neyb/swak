package io.neyb.swak.reader.provider.type

import io.neyb.swak.reader.provider.request.BodyReaderRequestProvider

interface BodyReaderTypeProvider {
    fun <B> forClass(target: Class<B>): BodyReaderRequestProvider<B>?
}