package io.neyb.swak.config.configurer

import io.neyb.swak.config.configurable.SubRouteConfigurable
import io.neyb.swak.handler.converter.reader.provider.type.BodyReaderTypeProvider
import io.neyb.swak.handler.interceptor.errorHandler.ErrorHandler
import io.neyb.swak.http.*
import io.reactivex.Single

class SubRouteConfigurer(
        private val subRouteHandler: SubRouteConfigurable
) : IntercepterConfigurer by subRouteHandler, RouterConfigurer by subRouteHandler{

    override fun addContentReaderProvider(bodyReaderTypeProvider: BodyReaderTypeProvider) =
            subRouteHandler.addContentReaderProvider(bodyReaderTypeProvider)

    inline fun <reified B> handleTyped(method: Method, path: String, noinline handler: (Request<B>) -> Single<Response>) {
        handleTyped(path, method, B::class.java, handler)
    }

    inline fun <reified E : Throwable> handleError(noinline errorHandler: (E) -> Response) {
        handleError(ErrorHandler.of(errorHandler))
    }
}