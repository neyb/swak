package io.neyb.swak.chain

import io.neyb.swak.chain.interceptor.after.AfterInterceptors
import io.neyb.swak.chain.interceptor.before.BeforeInterceptors
import io.neyb.swak.chain.interceptor.errorHandler.ErrorHandlers
import io.neyb.swak.chain.route.Routes
import io.neyb.swak.http.*
import io.reactivex.Single
import mu.KLogging

class Chain {
    companion object : KLogging()

    val beforeInterceptors = BeforeInterceptors<String>()
    val routes: Routes = Routes()
    val afterInterceptors = AfterInterceptors<Any>()
    val errorHandlers = ErrorHandlers()

    fun handle(request: Request<String>): Single<Response> =
            Single.just(request)
                    .flatMap { request -> beforeInterceptors.updateRequest(request) }
                    .flatMap { request -> routes.handle(request) }
                    .flatMap { response -> afterInterceptors.onAfter(request, response) }
                    .onErrorReturn { error -> errorHandlers.onError(error) ?: handleUnhandledError(error) }

    private fun handleUnhandledError(error: Throwable): Response {
        logger.error(error) { "a not handled error occured" }
        return Response(Code.INTERNAL_SERVER_ERROR)
    }
}