package io.neyb.swak.chain

import io.neyb.swak.chain.route.Routes
import io.neyb.swak.chain.route.interceptor.Interceptors
import io.neyb.swak.http.*
import io.reactivex.Single

class Chain {
    val interceptors: Interceptors = Interceptors()
    val routes: Routes = Routes()

    fun handle(request: Request): Single<Response> =
            Single.just(request)
                    .flatMap { interceptors.onBefore(it) }
                    .flatMap { routes.handle(it) }
                    .flatMap { interceptors.onAfter(request, it) }
                    .onErrorReturn { interceptors.onError(it) ?: handleUnhandledError(it) }

    private fun handleUnhandledError(error: Throwable): Response {
        println("a not handled error occured")
        error.printStackTrace()
        return Response(Status.INTERNAL_ERROR)
    }
}