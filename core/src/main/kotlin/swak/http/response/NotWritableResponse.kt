package swak.http.response

import swak.body.writer.BodyWriter

interface NotWritableResponse<out Body> : Response<Body> {
    fun withWriter(bodyWriter: BodyWriter<Body>): WritableResponse<Body>
}