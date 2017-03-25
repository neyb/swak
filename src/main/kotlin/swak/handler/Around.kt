package swak.handler

import io.reactivex.Single
import swak.http.requestContext.*
import swak.interceptor.after.AfterInterceptor
import swak.interceptor.after.ResponseUpdaters
import swak.interceptor.before.BeforeInterceptor
import swak.interceptor.before.RequestUpdaters
import swak.interceptor.errorHandler.ErrorHandlers
import swak.interceptor.errorHandler.ErrorRecover
import kotlin.properties.Delegates

internal class Around<Body>(
        private val before: BeforeInterceptor<Body>,
        private val handler: Handler<Body>,
        private val after: AfterInterceptor<Body>,
        private val errorHandlers: ErrorHandlers
) : Handler<Body> {

    class Builder<Body> {
        val before = RequestUpdaters.Builder<Body>()
        var innerHandler: HandlerBuilder<Body> by Delegates.notNull()
        val after = ResponseUpdaters.Builder<Body>()
        val errorHandlers = ErrorHandlers.Builder()

        private fun hasBehaviour() = before.hasBehaviour() || after.hasBehaviour() || errorHandlers.hasBehaviour()

        fun build() = build(innerHandler.build())

        private fun build(innerHandler: Handler<Body>) =
                if (hasBehaviour()) Around(before.build(), innerHandler, after.build(), errorHandlers.build())
                else innerHandler
    }

    override fun handle(reqContext: UpdatableRequestContext<Body>): Single<UpdatableResponseContext<Body>> =
            Single.just(reqContext)
                    .flatMap { before.updateRequestContext(it) }
                    .flatMap { handler.handle(it) }
                    .flatMap { after.updateRequestContext(it) }
                    .handleError(reqContext)

    override fun toString() = handler.toString()

    private fun Single<UpdatableResponseContext<Body>>.handleError(reqContext: RequestContext<Body>): Single<UpdatableResponseContext<Body>> =
            this.map<ErrorRecover<Body>> { ErrorRecover.SafeErrorRecover(it) }
                    .onErrorReturn { error: Throwable ->
                        val possibleResponse = errorHandlers.onError(error)

                        ErrorRecover.RethrowErrorRecover(
                                error,
                                if (possibleResponse != null)
                                    UpdatableResponseContext(reqContext, possibleResponse)
                                else
                                    null
                        )
                    }
                    .map(ErrorRecover<Body>::responseContext)
}