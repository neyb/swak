package swak.reader.provider.type

import swak.reader.provider.request.BodyReaderRequestProvider

interface BodyReaderTypeProvider {
    fun <B> forClass(target: Class<B>): BodyReaderRequestProvider<B>?
}