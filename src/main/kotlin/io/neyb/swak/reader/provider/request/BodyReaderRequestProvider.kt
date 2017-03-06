package io.neyb.swak.reader.provider.request

import io.neyb.swak.http.Request
import io.neyb.swak.reader.BodyReader

interface BodyReaderRequestProvider<B> {
    fun forRequest(request: Request<String>): BodyReader<B>
}