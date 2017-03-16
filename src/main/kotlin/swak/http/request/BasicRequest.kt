package swak.http.request

import io.reactivex.Single
import swak.http.Headers
import swak.http.request.Method

interface BasicRequest {
    val headers: Headers
    val path: String
    val method: Method
    val body: Single<String>
}