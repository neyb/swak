package swak.http.response

import swak.body.writer.BodyWriter
import swak.http.MutableHeaders

fun NoBodyResponse(status: Code = Code.OK, headers: MutableHeaders = MutableHeaders()) =
        SimpleResponse(status, headers, Unit)

typealias NoBodyResponse = SimpleResponse<Unit>

class SimpleResponse<out Body>(
        override val status: Code = Code.OK,
        override val headers: MutableHeaders = MutableHeaders(),
        override val body: Body
) : NotWritableResponse<Body> {

    override fun withWriter(bodyWriter: BodyWriter<Body>): WritableResponse<Body> =
            if (body is String)
                @Suppress("UNCHECKED_CAST") //this is safe because body is a string
                StringResponse(status, headers, body) as WritableResponse<Body>
            else
                SimpleWritableResponse(this, bodyWriter)
}