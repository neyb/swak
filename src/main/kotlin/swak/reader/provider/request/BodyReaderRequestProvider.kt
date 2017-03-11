package swak.reader.provider.request

import swak.http.UpdatableRequest
import swak.reader.BodyReader

interface BodyReaderRequestProvider<out B> {
    fun forRequest(request: UpdatableRequest<String>): BodyReader<B>
}