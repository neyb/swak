package swak.http.request

import io.reactivex.Single
import swak.http.*
import swak.body.reader.BodyReader

class UpdatableRequest<out B> constructor(
        private val basicRequest: BasicRequest,
        private val bodyReader: BodyReader<B>,
        private val pathParamExtractor: PathParamExtractor = NoPathParamExtractor
) : Request<B> {
    override val headers
        get() = basicRequest.headers

    override val path
        get() = basicRequest.path

    override val pathParams by lazy {
        pathParamExtractor.extractFrom(path)
    }

    override val queryParams
        get() = basicRequest.queryParam

    override val method
        get() = basicRequest.method

    override val body: Single<out B> by lazy {
        basicRequest.body.map{ bodyReader.read(it) }
    }

    fun <NewBody> withBodyReader(newBodyReader: BodyReader<NewBody>) =
            UpdatableRequest(basicRequest, newBodyReader, pathParamExtractor)

    internal fun withPathParamExtractor(pathParamExtractor: PathParamExtractor) =
            UpdatableRequest(basicRequest, bodyReader, pathParamExtractor)
}

