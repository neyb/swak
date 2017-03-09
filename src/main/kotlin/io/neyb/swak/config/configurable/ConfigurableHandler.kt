package io.neyb.swak.config.configurable

import io.neyb.swak.config.configurer.HandlerConfigurer
import io.neyb.swak.handler.*
import io.neyb.swak.handler.converter.reader.provider.type.BodyReaderTypeProvider
import io.neyb.swak.handler.converter.reader.provider.type.BodyReaderTypeProviders
import io.neyb.swak.http.Request
import io.neyb.swak.http.Response
import io.reactivex.Single

interface ConfigurableHandler<B> : HandlerConfigurer, HandlerBuilder<B> {
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
