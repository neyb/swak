package io.neyb.swak.handler.converter.reader.provider.request

import io.neyb.swak.handler.converter.reader.BodyReader
import io.neyb.swak.http.UpdatableRequest

class BodyReaderRequestProviders<B>(
        private val requestDependentBodyReaders: List<BodyReaderRequestProvider<B>>
) : BodyReaderRequestProvider<B> {
    override fun forRequest(request: UpdatableRequest<String>): BodyReader<B> =
            requestDependentBodyReaders
                    .mapNotNull { it.forRequest(request) }
                    .firstOrNull()
                    ?: throw NoReaderFoundForRequest(request)
}