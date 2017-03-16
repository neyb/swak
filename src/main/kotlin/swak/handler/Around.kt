package swak.handler

import io.reactivex.Single
import swak.http.response.Response
import swak.http.request.UpdatableRequest
import swak.interceptor.after.AfterInterceptors
import swak.interceptor.before.BeforeInterceptor
import swak.interceptor.before.BeforeInterceptors
import swak.interceptor.errorHandler.ErrorHandlers
import swak.interceptor.errorHandler.ErrorRecover
import kotlin.properties.Delegates

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

    override fun toString() = handler.toString()

    class Builder<Body> {
        val before = BeforeInterceptors.Builder<Body>()
        var innerHandler: HandlerBuilder<Body> by Delegates.notNull()
        val after = AfterInterceptors.Builder<Body>()
        val errorHandlers = ErrorHandlers.Builder()

        private fun hasBehaviour() = before.hasBehaviour() || after.hasBehaviour() || errorHandlers.hasBehaviour()

        fun build() = build(innerHandler.build())

        private fun build(innerHandler: Handler<Body>) =
                if(hasBehaviour()) Around(before.build(),innerHandler, after.build(), errorHandlers.build())
                else innerHandler
    }
}