package swak.http.response

import swak.http.MutableHeaders

class StringResponse(
        override val status: Code = Code.OK,
        override val headers: MutableHeaders = MutableHeaders(),
        override val body: String? = null
) : WritableResponse<String> {
    override val writableBody = body ?: ""
}