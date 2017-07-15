package swak.http.request

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

    //TODO make it lazy
    suspend override fun body() =
        basicRequest.body().let{ bodyReader.read(it) }

    fun <NewBody> withBodyReader(newBodyReader: BodyReader<NewBody>) =
            UpdatableRequest(basicRequest, newBodyReader, pathParamExtractor)

    internal fun withPathParamExtractor(pathParamExtractor: PathParamExtractor) =
            UpdatableRequest(basicRequest, bodyReader, pathParamExtractor)
}

