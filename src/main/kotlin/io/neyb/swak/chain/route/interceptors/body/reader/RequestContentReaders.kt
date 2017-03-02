package io.neyb.swak.chain.route.interceptors.body.reader

import io.neyb.swak.chain.interceptor.before.BeforeInterceptor
import io.neyb.swak.http.Request
import io.reactivex.Single

class RequestContentReaders<B>(
        private val requestContentReaders: List<RequestContentReader<B>>
) : BeforeInterceptor<String, B> {
    override fun updateRequest(request: Request<String>): Single<Request<B>> {
        val acceptingContentReaders = requestContentReaders.filter { it.accept(request) }
        return when (acceptingContentReaders.size) {
            0 -> throw NoContentReaderFound(request)
            1 -> acceptingContentReaders[0]
            else -> throw SeveralContentReaderFound(request)
        }.updateRequest(request)
    }
}

