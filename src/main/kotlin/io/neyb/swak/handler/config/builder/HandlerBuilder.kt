package io.neyb.swak.handler.config.builder

import io.neyb.swak.handler.RequestHandler

interface HandlerBuilder<B> {
    fun build(): RequestHandler<B>
}