package swak.http.response

import swak.body.writer.BodyWriter

class SimpleWritableResponse<out Body : Any>(
        val response: NotWritableResponse<Body>,
        bodyWriter: BodyWriter<Body>
) : Response<Body> by response, WritableResponse<Body> {
    override val writableBody by lazy { body?.let(bodyWriter::write) ?: "" }
}