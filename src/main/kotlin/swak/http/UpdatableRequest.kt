package swak.http

import io.reactivex.Single
import swak.reader.BodyReader

class UpdatableRequest<B> internal constructor(
        private val basicRequest: BasicRequest,
        private val bodyReader: BodyReader<B>,
        private val pathParamExtractor: PathParamExtractor
) : Request<B> {
    override val headers: Headers
        get() = basicRequest.headers

    override val path: String
        get() = basicRequest.path

    override val pathParams by lazy {
        pathParamExtractor.extractFrom(path)
    }

    override val method: Method
        get() = basicRequest.method

    override val body: Single<B?> by lazy {
        basicRequest.body.map { bodyReader.read(it) }
    }

    override val additionalData: AdditionalData = AdditionalData()

    fun <NewBody> withBodyReader(newBodyReader: BodyReader<NewBody>) =
            UpdatableRequest(basicRequest, newBodyReader, pathParamExtractor)

    internal fun withPathParamExtractor(pathParamExtractor: PathParamExtractor) =
            UpdatableRequest(basicRequest, bodyReader, pathParamExtractor)
}

