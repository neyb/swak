package swak.body.reader.provider

import swak.body.reader.BodyReader
import swak.body.reader.provider.request.BodyReaderChooser
import swak.body.reader.provider.type.BodyReaderChooserProvider
import swak.http.request.UpdatableRequest

inline fun <reified T> BodyReader<T>.useAlways() =
        AlwaysUseReader(T::class.java, this)

class AlwaysUseReader<out T>(
        private val target: Class<T>,
        bodyReader: BodyReader<T>
) : BodyReader<T> by bodyReader, BodyReaderChooser<T>, BodyReaderChooserProvider {
    override fun <B> forClass(target: Class<B>) =
            @Suppress("UNCHECKED_CAST")
            if (target == this.target) this as BodyReaderChooser<B>
            else null

    override fun forRequest(request: UpdatableRequest<String>) = this
}