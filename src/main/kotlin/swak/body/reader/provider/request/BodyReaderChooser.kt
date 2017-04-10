package swak.body.reader.provider.request

import swak.http.request.UpdatableRequest
import swak.body.reader.BodyReader

interface BodyReaderChooser<out B> : PotentialBodyReaderChooser<B> {
    override fun forRequest(request: UpdatableRequest<String>): BodyReader<B>
}