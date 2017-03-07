package io.neyb.swak.handler

interface HandlerBuilder<B> {
    fun build(): RequestHandler<B>
}