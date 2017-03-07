package io.neyb.swak.handler.converter.reader.provider.request

import io.neyb.swak.http.Request
import io.neyb.swak.handler.converter.reader.BodyReader
import io.neyb.swak.handler.converter.reader.provider.request.NoReaderFoundForRequest

class BodyReaderRequestProviders<B>(
        private val requestDependentBodyReaders: List<BodyReaderRequestProvider<B>>
) : BodyReaderRequestProvider<B> {
    override fun forRequest(request: Request<String>): BodyReader<B> =
            requestDependentBodyReaders
                    .mapNotNull { it.forRequest(request) }
                    .firstOrNull()
                    ?: throw NoReaderFoundForRequest(request)
}