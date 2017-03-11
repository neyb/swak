package swak.http

import io.reactivex.Single

interface BasicRequest {
    val headers: Headers
    val path: String
    val method: Method
    val body: Single<String>
}