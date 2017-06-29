package swak.http.response

import swak.http.MutableHeaders

class ErrorResponse<out ErrBody>(
        override val status: Code,
        override val headers: MutableHeaders = MutableHeaders(),
        val errBody: ErrBody
) : Response<Nothing> {
    companion object {
        fun withoutBody(status: Code, headers: MutableHeaders = MutableHeaders()) =
                ErrorResponse(status, headers, Unit)
    }

    override val body: Nothing get() = TODO() //TODO add an exception
}