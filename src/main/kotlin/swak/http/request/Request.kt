package swak.http.request

import io.reactivex.Single
import swak.http.*
import swak.http.requestContext.AdditionalData

interface Request<B> {
    val headers: Headers
    val path: String
    val pathParams: Map<String, String>
    val method: Method
    val body: Single<B?>
}