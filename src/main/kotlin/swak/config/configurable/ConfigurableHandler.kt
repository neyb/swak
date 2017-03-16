package swak.config.configurable

import io.reactivex.Single
import swak.config.configurer.HandlerConfigurer
import swak.handler.*
import swak.http.request.Request
import swak.http.response.Response
import swak.reader.provider.type.BodyReaderTypeProvider
import swak.reader.provider.type.BodyReaderTypeProviders

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

