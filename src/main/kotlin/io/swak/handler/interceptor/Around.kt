package io.swak.handler.interceptor

import io.swak.handler.Handler
import io.swak.handler.interceptor.after.AfterInterceptors
import io.swak.handler.interceptor.before.BeforeInterceptor
import io.swak.handler.interceptor.before.BeforeInterceptors
import io.swak.handler.interceptor.errorHandler.ErrorHandlers
import io.swak.handler.interceptor.errorHandler.ErrorRecover
import io.swak.http.UpdatableRequest
import io.swak.http.Response
import io.reactivex.Single

internal class Around<Body>(
        private val before: BeforeInterceptor<Body>,
        private val handler: Handler<Body>,
        private val after: AfterInterceptors<Body>,
        private val errorHandlers: ErrorHandlers
) : Handler<Body> {

    override fun handle(request: UpdatableRequest<Body>): Single<Response> =
            Single.just(request)
                    .flatMap { before.updateRequest(it) }
                    .flatMap { request ->
                        handler.handle(request)
                                .flatMap { after.onAfter(request, it) }
                    }
                    .map<ErrorRecover> { ErrorRecover.SafeErrorRecover(it) }
                    .onErrorReturn { error -> ErrorRecover.RethrowErrorRecover(error, errorHandlers.onError(error)) }
                    .map { it.response }

    class Builder<Body> {
        val before = BeforeInterceptors.Builder<Body>()
        val after = AfterInterceptors.Builder<Body>()
        val errorHandlersBuilder = ErrorHandlers.Builder()

        fun hasBehaviour() = before.hasBehaviour() || after.hasBehaviour() || errorHandlersBuilder.hasBehaviour()

        fun build(handler: Handler<Body>) = Around(
                before.build(),
                handler,
                after.build(),
                errorHandlersBuilder.build()
        )
    }
}