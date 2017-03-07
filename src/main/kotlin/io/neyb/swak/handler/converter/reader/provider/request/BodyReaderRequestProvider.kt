package io.neyb.swak.handler.converter.reader.provider.request

import io.neyb.swak.http.Request
import io.neyb.swak.handler.converter.reader.BodyReader

interface BodyReaderRequestProvider<B> {
    fun forRequest(request: Request<String>): BodyReader<B>
}