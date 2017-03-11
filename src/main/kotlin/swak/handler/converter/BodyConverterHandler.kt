package swak.handler.converter

import swak.handler.Handler
import swak.handler.converter.reader.provider.request.BodyReaderRequestProvider
import swak.http.UpdatableRequest

internal class BodyConverterHandler<Body>(
        private val readerProvider: BodyReaderRequestProvider<Body>,
        private val handler: Handler<Body>
) : Handler<String> {
    override fun handle(request: UpdatableRequest<String>) =
            handler.handle(request.withBodyReader(readerProvider.forRequest(request)))
}