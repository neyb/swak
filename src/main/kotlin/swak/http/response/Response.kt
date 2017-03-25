package swak.http.response

data class Response(
        val status: Code = Code.OK,
        val body: Any? = null
)