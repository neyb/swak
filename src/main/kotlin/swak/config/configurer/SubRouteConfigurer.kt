package swak.config.configurer

import io.reactivex.Single
import swak.body.reader.provider.type.BodyReaderChooserProvider
import swak.body.writer.provider.type.BodyWriterChooserProvider
import swak.config.configurable.SubRouteConfigurable
import swak.http.request.Method
import swak.http.request.Request
import swak.http.response.NotWritableResponse
import swak.http.response.SimpleResponse
import swak.interceptor.errorHandler.ErrorHandler

class SubRouteConfigurer internal constructor(
        private val subRouteHandler: SubRouteConfigurable
) : AroundConfigurer by subRouteHandler, RouterConfigurer by subRouteHandler{

    override fun addContentReaderProvider(bodyReaderChooserProvider: BodyReaderChooserProvider) =
            subRouteHandler.addContentReaderProvider(bodyReaderChooserProvider)

    override fun addContentWriterProvider(bodyWriterChooserProvider: BodyWriterChooserProvider) =
            subRouteHandler.addContentWriterProvider(bodyWriterChooserProvider)

    inline fun <reified OB : Any> handle(path: String, method: Method, noinline handler: (Request<String>) -> Single<out NotWritableResponse<OB>>) {
        handle(path, method, OB::class.java, handler)
    }

    inline fun <reified IB, reified OB : Any> handleTyped(method: Method, path: String, noinline handler: (Request<IB>) -> Single<out NotWritableResponse<OB>>) {
        handleTyped(path, method, IB::class.java, OB::class.java, handler)
    }

    inline fun <reified E : Throwable, reified OB : Any> handleError(noinline errorHandler: (E) -> SimpleResponse<OB>) {
        handleError(OB::class.java, ErrorHandler.of(errorHandler))
    }
}