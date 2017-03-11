package io.swak.config.configurer

import io.reactivex.Single
import io.swak.config.configurable.SubRouteConfigurable
import io.swak.handler.converter.reader.provider.type.BodyReaderTypeProvider
import io.swak.handler.interceptor.errorHandler.ErrorHandler
import io.swak.http.*

class SubRouteConfigurer internal constructor(
        private val subRouteHandler: SubRouteConfigurable
) : AroundConfigurer by subRouteHandler, RouterConfigurer by subRouteHandler{

    override fun addContentReaderProvider(bodyReaderTypeProvider: BodyReaderTypeProvider) =
            subRouteHandler.addContentReaderProvider(bodyReaderTypeProvider)

    inline fun <reified B> handleTyped(method: Method, path: String, noinline handler: (Request<B>) -> Single<Response>) {
        handleTyped(path, method, B::class.java, handler)
    }

    inline fun <reified E : Throwable> handleError(noinline errorHandler: (E) -> Response) {
        handleError(ErrorHandler.of(errorHandler))
    }
}