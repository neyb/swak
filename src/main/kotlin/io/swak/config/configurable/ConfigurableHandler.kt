package io.swak.config.configurable

import io.swak.config.configurer.HandlerConfigurer
import io.swak.handler.*
import io.swak.handler.converter.reader.provider.type.BodyReaderTypeProvider
import io.swak.handler.converter.reader.provider.type.BodyReaderTypeProviders
import io.swak.http.Request
import io.swak.http.Response
import io.reactivex.Single
import io.swak.handler.*

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

