package swak.config.configurable

import io.reactivex.Single
import swak.body.reader.provider.type.*
import swak.body.writer.provider.type.*
import swak.config.configurer.HandlerConfigurer
import swak.handler.HandlerBuilder
import swak.handler.NotWritable.*
import swak.http.request.context.RequestContext
import swak.http.response.NotWritableResponse

internal interface ConfigurableHandler<reqBody> : HandlerConfigurer, HandlerBuilder<reqBody> {
    val parent: ConfigurableHandler<*>?
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

    fun <IB, OB> ((RequestContext<IB>) -> Single<out NotWritableResponse<OB>>).asRequestHandler(): NotWritableHandler<IB, OB> =
            FinalHandler(this)
}

