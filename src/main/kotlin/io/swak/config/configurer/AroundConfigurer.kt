package io.swak.config.configurer

import io.swak.handler.interceptor.errorHandler.ErrorHandler

interface AroundConfigurer :HandlerConfigurer {
    fun handleError(errorHandler: ErrorHandler)
}