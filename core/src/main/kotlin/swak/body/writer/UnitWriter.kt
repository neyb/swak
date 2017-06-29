package swak.body.writer

import swak.body.writer.provider.request.BodyWriterChooser
import swak.body.writer.provider.type.BodyWriterChooserProvider
import swak.http.request.Request
import swak.http.response.Response

object UnitWriter : BodyWriter<Unit> {
    override fun write(body: Unit): String = ""
}