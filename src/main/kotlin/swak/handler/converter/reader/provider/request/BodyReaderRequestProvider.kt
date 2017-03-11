package swak.handler.converter.reader.provider.request

import swak.handler.converter.reader.BodyReader
import swak.http.UpdatableRequest

interface BodyReaderRequestProvider<out B> {
    fun forRequest(request: UpdatableRequest<String>): BodyReader<B>
}