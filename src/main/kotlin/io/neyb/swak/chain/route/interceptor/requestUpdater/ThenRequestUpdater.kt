package io.neyb.swak.chain.route.interceptor.requestUpdater

import io.neyb.swak.http.Request

class ThenRequestUpdater(first: RequestUpdater, second: RequestUpdater) : RequestUpdater {
    private val requestUpdaters: MutableList<RequestUpdater> = mutableListOf(first, second)

    override fun update(request: Request): Request {
        var resultingRequest = request
        for (requestUpdater in requestUpdaters)
            resultingRequest = requestUpdater.update(resultingRequest)
        return resultingRequest
    }

    override fun then(other: RequestUpdater): RequestUpdater {
        requestUpdaters += other
        return this
    }
}