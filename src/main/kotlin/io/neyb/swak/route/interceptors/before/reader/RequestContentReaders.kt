package io.neyb.swak.route.interceptors.before.reader

import io.neyb.swak.http.Request
import io.reactivex.Single

class RequestContentReaders<Before, After>(
        private val requestContentReaders: List<RequestContentReader<Before, After>>
) : RequestContentReader<Before, After> {
    override fun accept(request: Request<Before>) = true

    override fun updateRequest(request: Request<Before>): Single<Request<After>> {
        val acceptingContentReaders = requestContentReaders.filter { it.accept(request) }
        return when (acceptingContentReaders.size) {
            0 -> throw NoContentReaderFound(request)
            1 -> acceptingContentReaders[0]
            else -> throw SeveralContentReaderFound(request)
        }.updateRequest(request)
    }
}

