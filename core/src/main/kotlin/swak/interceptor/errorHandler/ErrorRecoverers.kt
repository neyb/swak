package swak.interceptor.errorHandler

import swak.body.writer.provider.type.BodyWriterChooserProviders
import swak.http.request.Request
import swak.http.response.WritableResponse
import java.util.*

internal class ErrorRecoverers(
        private val errorHandlers: List<ErrorRecoverer<Any>>
) {
    fun onError(request: Request<*>, error: Throwable): WritableResponse<Any>? =
            errorHandlers.asSequence()
                    .mapNotNull { it.recoverFrom(request, error) }
                    .firstOrNull()

    override fun toString() = errorHandlers.toString()

    class Builder {
        class HandlerByClass {
            private val handlersByClass = mutableMapOf<Class<Any>, MutableList<ErrorHandler<Any>>>()

            fun <OB : Any> put(returnedTyped: Class<out OB>, errorHandler: ErrorHandler<OB>) {
                @Suppress("UNCHECKED_CAST")
                handlersByClass.getOrPut(returnedTyped as Class<Any>, { ArrayList() }).add(errorHandler)
            }

            fun toErrorHandlers(writerChooserProvider: BodyWriterChooserProviders) =
                    handlersByClass.flatMap { entry ->
                        val writerChooser = writerChooserProvider.forClass(entry.key)
                        entry.value.map { ErrorRecoverer(it, writerChooser) }
                    }

            fun isEmpty() = handlersByClass.isEmpty()
        }

        private val handlerByClass = HandlerByClass()
        fun <OB : Any> add(target: Class<OB>, errorHandler: ErrorHandler<OB>) {
            handlerByClass.put(target, errorHandler)
        }

        fun hasBehaviour() = !handlerByClass.isEmpty()
        fun build(writersProvider: BodyWriterChooserProviders) =
                ErrorRecoverers(handlerByClass.toErrorHandlers(writersProvider))
    }
}