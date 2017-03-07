package io.neyb.swak.config.configurable

import io.neyb.swak.config.configurer.HandlerConfigurer
import io.neyb.swak.handler.Handler
import io.neyb.swak.handler.HandlerBuilder
import io.neyb.swak.handler.converter.reader.provider.type.BodyReaderTypeProvider
import io.neyb.swak.handler.converter.reader.provider.type.BodyReaderTypeProviders
import io.neyb.swak.http.Request
import io.neyb.swak.http.Response
import io.reactivex.Single

interface ConfigurableHandler<B> : HandlerConfigurer, HandlerBuilder<B> {
    val parent: ConfigurableHandler<*>?
    val bodyReaderTypeProviders: BodyReaderTypeProviders

    override fun addContentReaderProvider(bodyReaderTypeProvider: BodyReaderTypeProvider) {
        bodyReaderTypeProviders.add(bodyReaderTypeProvider)
    }

    private class SimpleHandler<B>(private val handler: (Request<B>) -> Single<Response>) : Handler<B> {
        override fun handle(request: Request<B>) = handler(request)
    }
    fun <B> ((Request<B>) -> Single<Response>).asRequestHandler():Handler<B> = SimpleHandler(this)
}