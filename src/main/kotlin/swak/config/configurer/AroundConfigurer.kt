package swak.config.configurer

import swak.interceptor.errorHandler.ErrorHandler

interface AroundConfigurer : HandlerConfigurer {
    fun handleError(errorHandler: ErrorHandler)
}