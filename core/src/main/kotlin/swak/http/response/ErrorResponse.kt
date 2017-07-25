package swak.http.response

import swak.http.MutableHeaders

class ErrorResponse<out ErrBody>(
        override val status: Code,
        override val headers: MutableHeaders = MutableHeaders(),
        val errBody: ErrBody
) : Response<Nothing> {

    override val body: Nothing get() = throw NoBodyBecauseOfAnError()
}