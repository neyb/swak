package swak.http.request.context

import swak.http.request.UpdatableRequest
import swak.body.reader.BodyReader

data class UpdatableRequestContext<out BodyIn>(
        override val request: UpdatableRequest<BodyIn>
) : RequestContext<BodyIn>{
    fun <NewBody> withRequest(request: UpdatableRequest<NewBody>) =
            UpdatableRequestContext(request)

    fun <NewBody> withBodyReader(newBodyReader: BodyReader<NewBody>) =
            withRequest(request.withBodyReader(newBodyReader))
}