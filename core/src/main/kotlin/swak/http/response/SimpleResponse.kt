package swak.http.response

import swak.body.writer.BodyWriter
import swak.http.MutableHeaders

typealias NoBodyResponse = SimpleResponse<Unit>

class SimpleResponse<out Body : Any>(
        override val status: Code = Code.OK,
        override val headers: MutableHeaders = MutableHeaders(),
        override val body: Body? = null
) : NotWritableResponse<Body> {
    override fun withWriter(bodyWriter: BodyWriter<Body>): WritableResponse<Body> =
            if (body is String)
                @Suppress("UNCHECKED_CAST") //this is safe because body is a string
                StringResponse(status, headers, body) as WritableResponse<Body>
            else
                SimpleWritableResponse(this, bodyWriter)
}