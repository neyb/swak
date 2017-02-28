package io.neyb.swak.chain.route.interceptor.requestUpdater

import io.neyb.swak.http.Request
import io.reactivex.Single

class ThenRequestUpdater(first: RequestUpdater, second: RequestUpdater) : RequestUpdater {
    private val requestUpdaters: MutableList<RequestUpdater> = mutableListOf(first, second)

    override fun update(request: Request): Single<Request> {
        var resultingRequest = Single.just(request)
        for (requestUpdater in requestUpdaters)
            resultingRequest = resultingRequest.flatMap { requestUpdater.update(it) }
        return resultingRequest
    }

    override fun then(other: RequestUpdater): RequestUpdater {
        requestUpdaters += other
        return this
    }
}