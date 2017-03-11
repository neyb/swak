package io.swak.handler.converter.reader.provider.request

import io.swak.handler.converter.reader.BodyReader
import io.swak.http.UpdatableRequest

interface BodyReaderRequestProvider<out B> {
    fun forRequest(request: UpdatableRequest<String>): BodyReader<B>
}