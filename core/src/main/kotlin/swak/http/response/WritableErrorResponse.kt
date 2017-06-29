package swak.http.response

import swak.body.writer.BodyWriter

class WritableErrorResponse<out ErrBody>(
        val errorResponse: ErrorResponse<ErrBody>,
        bodyWriter: BodyWriter<ErrBody>
) : WritableResponse<Nothing>, Response<Nothing> by errorResponse {
    override val writableBody: String by lazy {
        errorResponse.errBody?.let(bodyWriter::write) ?: ""
    }
}