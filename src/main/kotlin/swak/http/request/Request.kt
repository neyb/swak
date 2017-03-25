package swak.http.request

import io.reactivex.Single
import swak.http.Headers

interface Request<out B> {
    val headers: Headers
    val path: String
    val pathParams: Map<String, String>
    val queryParams: Map<String, List<String>>
    val method: Method
    val body: Single<out B?>
}