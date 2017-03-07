package io.neyb.swak.config.configurer

import io.neyb.swak.handler.interceptor.errorHandler.ErrorHandler

interface IntercepterConfigurer:HandlerConfigurer {
    fun handleError(errorHandler: ErrorHandler)
}