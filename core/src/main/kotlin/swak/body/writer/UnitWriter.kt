package swak.body.writer

import swak.body.writer.provider.request.BodyWriterChooser
import swak.body.writer.provider.type.BodyWriterChooserProvider
import swak.http.request.Request
import swak.http.response.Response

object UnitWriter : BodyWriter<Unit>, BodyWriterChooser<Unit>, BodyWriterChooserProvider {
    override fun write(body: Unit): String = ""

    override fun <B> forClass(target: Class<B>) =
            @Suppress("UNCHECKED_CAST")
            if (target == Unit::class.java) this as BodyWriterChooser<B>
            else null

    override fun `for`(response: Response<Unit>, request: Request<*>) = this
}