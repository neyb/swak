package swak.config.configurable

import io.reactivex.Single
import swak.config.configurer.HandlerConfigurer
import swak.handler.*
import swak.handler.converter.reader.provider.type.BodyReaderTypeProvider
import swak.handler.converter.reader.provider.type.BodyReaderTypeProviders
import swak.http.Request
import swak.http.Response

internal interface ConfigurableHandler<B> : HandlerConfigurer, HandlerBuilder<B> {
    val parent: ConfigurableHandler<*>?
    val localPath: String?
    val path: String
        get() = (parent?.localPath ?: "") + (localPath ?: "")
    val bodyReaderTypeProviders: BodyReaderTypeProviders

    override fun addContentReaderProvider(bodyReaderTypeProvider: BodyReaderTypeProvider) {
        bodyReaderTypeProviders.add(bodyReaderTypeProvider)
    }

    fun <B> ((Request<B>) -> Single<Response>).asRequestHandler(): Handler<B> = FinalHandler(this)
}

