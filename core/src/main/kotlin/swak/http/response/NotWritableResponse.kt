package swak.http.response

import swak.body.writer.BodyWriter

interface NotWritableResponse<out Body:Any> : Response<Body> {
    fun withWriter(bodyWriter: BodyWriter<Body>): WritableResponse<Body>
}