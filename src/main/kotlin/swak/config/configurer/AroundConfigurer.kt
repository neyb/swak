package swak.config.configurer

import swak.handler.interceptor.errorHandler.ErrorHandler

interface AroundConfigurer : HandlerConfigurer {
    fun handleError(errorHandler: ErrorHandler)
}