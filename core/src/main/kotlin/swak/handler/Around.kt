package swak.handler

import swak.body.writer.provider.type.BodyWriterChooserProviders
import swak.http.request.context.*
import swak.http.response.context.UpdatableResponseContext
import swak.interceptor.after.*
import swak.interceptor.before.*
import swak.interceptor.errorHandler.*
import java.lang.Exception
import kotlin.properties.Delegates

internal class Around<IB, out OB>(
        private val before: BeforeInterceptor<IB>,
        private val handler: Handler<IB, OB>,
        private val after: AfterInterceptor<IB, OB>,
        private val errorHandlers: ErrorRecoverers
) : Handler<IB, OB> {

    //TODO handle error
    override suspend fun handle(reqContext: UpdatableRequestContext<IB>): UpdatableResponseContext<IB, OB> {
        try {
            return reqContext.let { before.updateRequestContext(it) }
                    .let { handler.handle(it) }
                    .let { after.updateRequestContext(it) }
        } catch (e: Exception) {
            return UpdatableResponseContext(reqContext, errorHandlers.onError(reqContext.request, e) ?: throw e)
        }
    }

    override fun toString() = handler.toString()

    class Builder<IB, OB> {
        val before = RequestUpdaters.Builder<IB>()
        var innerHandler: HandlerBuilder<IB, OB> by Delegates.notNull()
        val after = ResponseUpdaters.Builder<IB, OB>()
        val errorRecoverers = ErrorRecoverers.Builder()

        fun build(writersProvider: BodyWriterChooserProviders) = build(
                writersProvider,
                innerHandler.build())

        private fun build(writersProvider: BodyWriterChooserProviders, innerHandler: Handler<IB, OB>) =
                if (hasBehaviour())
                    Around(
                            before.build(),
                            innerHandler,
                            after.build(),
                            errorRecoverers.build(writersProvider))
                else innerHandler

        private fun hasBehaviour() = before.hasBehaviour() || after.hasBehaviour() || errorRecoverers.hasBehaviour()
    }
}