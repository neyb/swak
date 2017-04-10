package swak.body.reader.provider.request

import swak.http.request.UpdatableRequest
import swak.body.reader.BodyReader

internal class BodyReaderRequestProviders<out B>(
        private val requestDependentBodyReaders: List<PotentialBodyReaderChooser<B>>
) : BodyReaderChooser<B> {
    override fun forRequest(request: UpdatableRequest<String>) =
            requestDependentBodyReaders
                    .mapNotNull { it.forRequest(request) }
                    .firstOrNull()
                    ?: throw NoReaderFoundForRequest(request)
}