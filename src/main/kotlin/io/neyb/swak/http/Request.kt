package io.neyb.swak.http

import io.neyb.swak.reader.BodyReader
import io.reactivex.Single

class Request<B>(
        private val basicRequest: BasicRequest,
        private val bodyReader: BodyReader<B>,
        val pathParams: Map<String, String> = emptyMap()
) {
    val headers: Headers
        get() = basicRequest.headers

    val path: String
        get() = basicRequest.path

    val method: Method
        get() = basicRequest.method

    val body: Single<B?> by lazy {
        basicRequest.body.map { bodyReader.read(it) }
    }

    val additionalData: AdditionalData = AdditionalData()

    fun <NewBody> withBodyReader(newBodyReader: BodyReader<NewBody>) =
            Request(basicRequest, newBodyReader, pathParams)

    fun withPathParam(newPathParams: Map<String, String>) =
            Request(basicRequest, bodyReader, pathParams + newPathParams)
}
