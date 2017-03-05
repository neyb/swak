package io.neyb.swak.route

import io.neyb.swak.route.interceptors.after.AfterInterceptors
import io.neyb.swak.route.interceptors.errorHandler.ErrorHandlers
import io.neyb.swak.route.matcher.RequestMatcher
import io.neyb.swak.http.Request
import io.neyb.swak.http.Response
import io.neyb.swak.route.interceptors.before.reader.BeforeRouteInterceptor
import io.reactivex.Single
import io.reactivex.exceptions.CompositeException

class SubRoute<Body, DelegatedBody>(
        private val requestMatcher: RequestMatcher<Body>,
        private val beforeRouteInterceptor: BeforeRouteInterceptor<Body, DelegatedBody>,
        private val route: Route<DelegatedBody>,
        private val afterRouteInterceptor: AfterInterceptors<Body>,
        private val errorHandlers: ErrorHandlers

) : Route<Body>, RequestMatcher<Body> by requestMatcher {

    override fun handle(request: Request<Body>): Single<Response> =
            Single.just(request)
                    .flatMap { beforeRouteInterceptor.updateRequest(it) }
                    .flatMap { request ->
                        route.handle(request)
                                .flatMap { afterRouteInterceptor.onAfter(request, it) }
                    }
                    .onErrorReturn { error ->
                        val errorToDealWith =
                                if (error is CompositeException && error.exceptions.size == 1) error.exceptions[0]
                                else error
                        errorHandlers.onError(errorToDealWith) ?: throw errorToDealWith
                    }
}