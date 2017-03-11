package swak.handler.converter.reader.provider.type

import swak.handler.converter.reader.provider.request.BodyReaderRequestProvider

interface BodyReaderTypeProvider {
    fun <B> forClass(target: Class<B>): BodyReaderRequestProvider<B>?
}