package swak.config.configurable

import io.reactivex.Single
import swak.config.configurer.HandlerConfigurer
import swak.handler.*
import swak.http.request.Request
import swak.http.response.SimpleResponse
import swak.body.reader.provider.type.BodyReaderChooserProvider
import swak.body.reader.provider.type.BodyReaderChooserProviders
import swak.body.writer.provider.type.BodyWriterChooserProvider
import swak.body.writer.provider.type.BodyWriterChooserProviders
import swak.http.response.NotWritableResponse

internal interface ConfigurableHandler<reqBody, out respBody> : HandlerConfigurer, HandlerBuilder<reqBody, respBody> {
    val parent: ConfigurableHandler<*, *>?
    val localPath: String?
    val path: String
        get() = (parent?.localPath ?: "") + (localPath ?: "")
    val bodyReaderTypeProviders: BodyReaderChooserProviders
    val bodyWriterTypeProviders: BodyWriterChooserProviders

    override fun addContentReaderProvider(bodyReaderChooserProvider: BodyReaderChooserProvider) {
        bodyReaderTypeProviders.add(bodyReaderChooserProvider)
    }

    override fun addContentWriterProvider(bodyWriterChooserProvider: BodyWriterChooserProvider) {
        bodyWriterTypeProviders.add(bodyWriterChooserProvider)
    }

    fun <IB, OB : Any> ((Request<IB>) -> Single<out NotWritableResponse<OB>>).asRequestHandler(): NotWritableHandler<IB, OB> =
            FinalHandler(this)
}

