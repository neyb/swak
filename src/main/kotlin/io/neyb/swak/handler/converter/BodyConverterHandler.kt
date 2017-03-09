package io.neyb.swak.handler.converter

import io.neyb.swak.handler.Handler
import io.neyb.swak.handler.converter.reader.provider.request.BodyReaderRequestProvider
import io.neyb.swak.http.UpdatableRequest

class BodyConverterHandler<Body>(
        private val readerProvider: BodyReaderRequestProvider<Body>,
        private val handler: Handler<Body>
) : Handler<String> {
    override fun handle(request: UpdatableRequest<String>) =
            handler.handle(request.withBodyReader(readerProvider.forRequest(request)))
}