package swak.http.request

import swak.http.Headers

interface Request<out B> {
    val headers: Headers
    val path: String
    val pathParams: Map<String, String>
    val queryParams: Map<String, List<String>>
    val method: Method
    suspend fun body(): B?
}