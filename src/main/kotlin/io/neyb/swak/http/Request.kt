package io.neyb.swak.http

import io.neyb.swak.http.AdditionalData
import io.reactivex.Single

class Request<B>(
        private val basicRequest: BasicRequest,
        private val bodyReader: (String) -> B?,
        val pathParams: Map<String, String> = emptyMap()
) {
    val headers: Headers
        get() = basicRequest.headers

    val path: String
        get() = basicRequest.path

    val method: Method
        get() = basicRequest.method

    val body: Single<B?> by lazy {
        basicRequest.body.map(bodyReader)
    }

    val additionalData: AdditionalData = AdditionalData()

    fun <T> withBodyReader(newBodyReader: (String) -> T?) =
            Request(basicRequest, newBodyReader, pathParams)

    fun withPathParam(pathParams: Map<String, String>) =
            Request(basicRequest, bodyReader, pathParams)
}
