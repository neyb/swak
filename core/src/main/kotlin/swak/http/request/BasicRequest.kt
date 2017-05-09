package swak.http.request

import io.reactivex.Single
import swak.http.Headers

interface BasicRequest {
    val headers: Headers
    val path: String
    val method: Method
    val body: Single<String>
    val queryParam: Map<String, List<String>>
}