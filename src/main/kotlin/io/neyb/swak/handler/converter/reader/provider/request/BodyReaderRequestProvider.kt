package io.neyb.swak.handler.converter.reader.provider.request

import io.neyb.swak.handler.converter.reader.BodyReader
import io.neyb.swak.http.UpdatableRequest

interface BodyReaderRequestProvider<B> {
    fun forRequest(request: UpdatableRequest<String>): BodyReader<B>
}