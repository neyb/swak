package swak.reader.provider.request

import swak.reader.BodyReader
import swak.http.UpdatableRequest

interface BodyReaderRequestProvider<out B> {
    fun forRequest(request: UpdatableRequest<String>): BodyReader<B>
}