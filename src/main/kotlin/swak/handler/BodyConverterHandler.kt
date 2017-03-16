package swak.handler

import swak.http.request.UpdatableRequest
import swak.reader.provider.request.BodyReaderRequestProvider

internal class BodyConverterHandler<Body>(
        private val readerProvider: BodyReaderRequestProvider<Body>,
        private val handler: Handler<Body>
) : Handler<String> {
    override fun handle(request: UpdatableRequest<String>) =
            handler.handle(request.withBodyReader(readerProvider.forRequest(request)))
}