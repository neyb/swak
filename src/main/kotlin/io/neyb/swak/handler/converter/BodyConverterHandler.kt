package io.neyb.swak.handler.converter

import io.neyb.swak.handler.RequestHandler
import io.neyb.swak.http.Request
import io.neyb.swak.reader.provider.request.BodyReaderRequestProvider

class BodyConverterHandler<Body>(
        private val readerProvider: BodyReaderRequestProvider<Body>,
        private val handler: RequestHandler<Body>
) : RequestHandler<String> {
    override fun handle(request: Request<String>) =
            handler.handle(request.withBodyReader(readerProvider.forRequest(request)))
}