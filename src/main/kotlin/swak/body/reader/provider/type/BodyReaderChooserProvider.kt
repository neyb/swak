package swak.body.reader.provider.type

import swak.body.reader.provider.request.BodyReaderChooser

interface BodyReaderChooserProvider {
    fun <B> forClass(target: Class<B>): BodyReaderChooser<B>?
}