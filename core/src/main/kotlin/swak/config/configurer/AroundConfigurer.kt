package swak.config.configurer

import swak.interceptor.errorHandler.ErrorHandler

interface AroundConfigurer : HandlerConfigurer {
    fun <T> handleError(target:Class<T>, errorHandler: ErrorHandler<T>)
}