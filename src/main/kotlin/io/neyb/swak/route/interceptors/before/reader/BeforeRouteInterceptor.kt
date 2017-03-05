package io.neyb.swak.route.interceptors.before.reader

import io.neyb.swak.route.interceptors.before.BeforeInterceptor
import io.neyb.swak.route.interceptors.before.BeforeInterceptors
import io.neyb.swak.http.Request
import io.reactivex.Single

class BeforeRouteInterceptor<BodyIn, BodyOut>(
        private val preContentNegociationInterceptor: BeforeInterceptor<BodyIn, BodyIn> = BeforeInterceptors(),
        private val requestContentReaders: BeforeInterceptor<BodyIn, BodyOut>,
        private val postContentNegociationInterceptor: BeforeInterceptor<BodyOut, BodyOut> = BeforeInterceptors()
) : BeforeInterceptor<BodyIn, BodyOut> {


    override fun updateRequest(request: Request<BodyIn>): Single<Request<BodyOut>> {
        return Single.just(request)
                .flatMap { preContentNegociationInterceptor.updateRequest(it) }
                .flatMap { requestContentReaders.updateRequest(it) }
                .flatMap { postContentNegociationInterceptor.updateRequest(it) }
    }
}