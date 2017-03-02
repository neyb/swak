package io.neyb.swak.chain.route.interceptors.body.reader

import io.neyb.swak.chain.interceptor.before.BeforeInterceptor
import io.neyb.swak.chain.interceptor.before.BeforeInterceptors
import io.neyb.swak.http.Request
import io.reactivex.Single
import java.util.*

class BeforeRouteInterceptor<B>(
        private val preContentNegociationInterceptor: BeforeInterceptor<String, String> = BeforeInterceptors(),
        private val requestContentReaders: BeforeInterceptor<String, B>,
        private val postContentNegociationInterceptor: BeforeInterceptor<B, B> = BeforeInterceptors()
) : BeforeInterceptor<String, B> {


    override fun updateRequest(request: Request<String>): Single<Request<B>> {
        return Single.just(request)
                .flatMap { preContentNegociationInterceptor.updateRequest(it) }
                .flatMap { requestContentReaders.updateRequest(it) }
                .flatMap { postContentNegociationInterceptor.updateRequest(it) }
    }
}