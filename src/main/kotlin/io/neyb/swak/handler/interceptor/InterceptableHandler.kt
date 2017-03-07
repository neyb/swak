package io.neyb.swak.handler.interceptor

import io.neyb.swak.handler.RequestHandler
import io.neyb.swak.handler.interceptor.after.AfterInterceptors
import io.neyb.swak.handler.interceptor.before.BeforeInterceptor
import io.neyb.swak.handler.interceptor.before.BeforeInterceptors
import io.neyb.swak.handler.interceptor.errorHandler.ErrorHandlers
import io.neyb.swak.http.Request
import io.neyb.swak.http.Response
import io.reactivex.Single
import io.reactivex.exceptions.CompositeException

class InterceptableHandler<Body>(
        private val beforeRouteInterceptor: BeforeInterceptor<Body>,
        private val handler: RequestHandler<Body>,
        private val afterRouteInterceptor: AfterInterceptors<Body>,
        private val errorHandlers: ErrorHandlers
) : RequestHandler<Body> {

    override fun handle(request: Request<Body>): Single<Response> =
            Single.just(request)
                    .flatMap { beforeRouteInterceptor.updateRequest(it) }
                    .flatMap { request ->
                        handler.handle(request)
                                .flatMap { afterRouteInterceptor.onAfter(request, it) }
                    }
                    .onErrorReturn { error ->
                        val errorToDealWith =
                                if (error is CompositeException && error.exceptions.size == 1) error.exceptions[0]
                                else error
                        errorHandlers.onError(errorToDealWith) ?: throw errorToDealWith
                    }

    class Builder<Body> {
        val before = BeforeInterceptors.Builder<Body>()
        val afterRouteInterceptorBuilder = AfterInterceptors.Builder<Body>()
        val errorHandlersBuilder = ErrorHandlers.Builder()

        fun build(handler: RequestHandler<Body>) = InterceptableHandler(
                before.build(),
                handler,
                afterRouteInterceptorBuilder.build(),
                errorHandlersBuilder.build()
        )
    }
}