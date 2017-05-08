package swak.body.writer.provider.type

import swak.body.writer.provider.request.BodyWriterChooser

interface BodyWriterChooserProvider {
    fun <B:Any> forClass(target: Class<B>): BodyWriterChooser<B>?
}