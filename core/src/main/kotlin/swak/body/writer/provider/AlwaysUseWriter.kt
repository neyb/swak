package swak.body.writer.provider

import swak.body.writer.BodyWriter
import swak.body.writer.provider.request.BodyWriterChooser
import swak.body.writer.provider.type.BodyWriterChooserProvider
import swak.http.request.Request
import swak.http.response.Response

inline fun <reified T : Any> BodyWriter<T>.useAlways() =
        AlwaysUseWriter(T::class.java, this)

class AlwaysUseWriter<in T : Any>(
        private val target: Class<T>,
        bodyWriter: BodyWriter<T>
) : BodyWriter<T> by bodyWriter, BodyWriterChooser<T>, BodyWriterChooserProvider {
    override fun <B : Any> forClass(target: Class<B>) =
            @Suppress("UNCHECKED_CAST")
            if (target == this.target) this as BodyWriterChooser<B>
            else null

    override fun `for`(response: Response<T>, request: Request<*>) = this
}