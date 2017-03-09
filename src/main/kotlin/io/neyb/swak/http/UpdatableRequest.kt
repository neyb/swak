package io.neyb.swak.http

import io.neyb.swak.handler.converter.reader.BodyReader
import io.reactivex.Single

class UpdatableRequest<B> internal constructor(
        private val basicRequest: BasicRequest,
        private val bodyReader: BodyReader<B>,
        override val pathParams: Map<String, String> = emptyMap()
) : Request<B> {
    override val headers: Headers
        get() = basicRequest.headers

    override val path: String
        get() = basicRequest.path

    override val method: Method
        get() = basicRequest.method

    override val body: Single<B?> by lazy {
        basicRequest.body.map { bodyReader.read(it) }
    }

    override val additionalData: AdditionalData = AdditionalData()

    fun <NewBody> withBodyReader(newBodyReader: BodyReader<NewBody>) =
            UpdatableRequest(basicRequest, newBodyReader, pathParams)

    fun withPathParam(newPathParams: Map<String, String>) =
            UpdatableRequest(basicRequest, bodyReader, pathParams + newPathParams)
}
