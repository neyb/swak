package swak.config.configurer

import io.reactivex.Single
import swak.body.reader.provider.type.BodyReaderChooserProvider
import swak.body.writer.provider.type.BodyWriterChooserProvider
import swak.config.configurable.SubRouteConfigurable
import swak.http.request.Method
import swak.http.request.context.RequestContext
import swak.http.response.*
import swak.interceptor.errorHandler.ErrorHandler

class SubRouteConfigurer internal constructor(
        private val subRouteHandler: SubRouteConfigurable
) : AroundConfigurer by subRouteHandler, RouterConfigurer by subRouteHandler {

    fun on(path: String, method: Method) = HandlerHelper(method, path, String::class.java)

    inner class HandlerHelper<out IB> internal constructor(
            private val method: Method,
            private val path: String,
            private val inputClass: Class<IB>
    ) {
        inline fun <reified T> withA() = withA(T::class.java)
        fun <T> withA(inputClass: Class<T>) = HandlerHelper(method, path, inputClass)

        inline infix fun <reified OB> answer(noinline handler: RequestContext<IB>.() -> Single<out NotWritableResponse<OB>>) =
                answer(OB::class.java, handler)
        fun <OB> answer(outputClass: Class<OB>, handler: RequestContext<IB>.() -> Single<out NotWritableResponse<OB>>) {
            this@SubRouteConfigurer.handle(
                    path,
                    method,
                    inputClass,
                    outputClass,
                    handler)
        }
    }

    override fun addContentReaderProvider(bodyReaderChooserProvider: BodyReaderChooserProvider) =
            subRouteHandler.addContentReaderProvider(bodyReaderChooserProvider)

    override fun addContentWriterProvider(bodyWriterChooserProvider: BodyWriterChooserProvider) =
            subRouteHandler.addContentWriterProvider(bodyWriterChooserProvider)

    inline fun <reified E : Throwable, reified OB : Any> handleError(noinline errorHandler: (E) -> SimpleResponse<OB>) {
        handleError(OB::class.java, ErrorHandler.of(errorHandler))
    }
}