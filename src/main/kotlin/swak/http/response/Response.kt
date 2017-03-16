package swak.http.response

import swak.http.response.Code

data class Response(
        val status: Code = Code.OK,
        val body: Any? = null
)