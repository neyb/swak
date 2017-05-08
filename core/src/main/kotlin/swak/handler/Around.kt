package swak.handler

import io.reactivex.Single
import swak.body.writer.provider.type.BodyWriterChooserProviders
import swak.http.request.context.*
import swak.http.response.context.UpdatableResponseContext
import swak.interceptor.after.*
import swak.interceptor.before.*
import swak.interceptor.errorHandler.*
import kotlin.properties.Delegates

internal class Around<IB>(
        private val before: BeforeInterceptor<IB>,
        private val handler: Handler<IB>,
        private val after: AfterInterceptor<IB>,
        private val errorHandlers: ErrorRecoverers
) : Handler<IB> {

    override fun handle(reqContext: UpdatableRequestContext<IB>): Single<UpdatableResponseContext<IB>> =
            Single.just(reqContext)
                    .flatMap { before.updateRequestContext(it) }
                    .flatMap { handler.handle(it) }
                    .flatMap { after.updateRequestContext(it) }
                    .handleError(reqContext)

    override fun toString() = handler.toString()

    private fun Single<UpdatableResponseContext<IB>>.handleError(reqContext: RequestContext<IB>): Single<UpdatableResponseContext<IB>> =
            this.map<RxErrorRecover<IB>> { RxErrorRecover.SafeRxErrorRecover(it) }
                    .onErrorReturn { error: Throwable -> recoverError(error, reqContext) }
                    .map(RxErrorRecover<IB>::responseContext)

    private fun recoverError(error: Throwable, reqContext: RequestContext<IB>): RxErrorRecover.RethrowRxErrorRecover<IB> {
        val possibleResponse = errorHandlers.onError(reqContext.request, error)

        return RxErrorRecover.RethrowRxErrorRecover(
                error,
                if (possibleResponse != null) {
                    UpdatableResponseContext(reqContext, possibleResponse)
                } else null
        )
    }

    class Builder<IB> {
        val before = RequestUpdaters.Builder<IB>()
        var innerHandler: HandlerBuilder<IB> by Delegates.notNull()
        val after = ResponseUpdaters.Builder<IB>()
        val errorRecoverers = ErrorRecoverers.Builder()

        fun build(writersProvider: BodyWriterChooserProviders) = build(
                writersProvider,
                innerHandler.build())

        private fun build(writersProvider: BodyWriterChooserProviders, innerHandler: Handler<IB>) =
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