package swak.http.response

import swak.body.writer.BodyWriter
import swak.http.MutableHeaders

class ErrorResponse<out ErrBody>(
        override val status: Code = Code.OK,
        override val headers: MutableHeaders = MutableHeaders(),
        override val body: ErrBody,
        private val bodyWriter: BodyWriter<ErrBody>
) : NotWritableResponse<ErrBody> {
    override fun withWriter(bodyWriter: BodyWriter<ErrBody>): WritableResponse<ErrBody> {
        return SimpleWritableResponse(this, bodyWriter)
    }
}