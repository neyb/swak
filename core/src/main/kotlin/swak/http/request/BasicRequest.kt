package swak.http.request

import swak.http.Headers

interface BasicRequest {
    val headers: Headers
    val path: String
    val method: Method
    suspend fun body(): String
    val queryParam: Map<String, List<String>>
}