package swak.handler.converter.reader.provider.request

import swak.handler.converter.reader.BodyReader
import swak.http.UpdatableRequest

internal class BodyReaderRequestProviders<B>(
        private val requestDependentBodyReaders: List<BodyReaderRequestProvider<B>>
) : BodyReaderRequestProvider<B> {
    override fun forRequest(request: UpdatableRequest<String>): BodyReader<B> =
            requestDependentBodyReaders
                    .mapNotNull { it.forRequest(request) }
                    .firstOrNull()
                    ?: throw NoReaderFoundForRequest(request)
}