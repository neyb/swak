package swak.body.reader.provider.request

import swak.http.request.UpdatableRequest
import swak.body.reader.BodyReader

interface PotentialBodyReaderChooser<out B> {
    fun forRequest(request: UpdatableRequest<String>): BodyReader<B>?
}