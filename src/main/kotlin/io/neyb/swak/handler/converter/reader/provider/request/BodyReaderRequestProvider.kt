package io.neyb.swak.handler.converter.reader.provider.request

import io.neyb.swak.handler.converter.reader.BodyReader
import io.neyb.swak.http.Request

interface BodyReaderRequestProvider<B> {
    fun forRequest(request: Request<String>): BodyReader<B>
}