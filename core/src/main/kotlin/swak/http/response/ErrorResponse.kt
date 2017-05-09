package swak.http.response

import swak.body.writer.BodyWriter
import swak.http.MutableHeaders

class ErrorResponse<out ErrBody>(
        override val status: Code = Code.OK,
        override val headers: MutableHeaders = MutableHeaders(),
        val errBody: ErrBody?,
        bodyWriter: BodyWriter<ErrBody>
) : WritableResponse<Nothing> {
    override val body: Nothing? get() = null

    override val writableBody: String by lazy {
        errBody?.let(bodyWriter::write) ?: ""
    }
}