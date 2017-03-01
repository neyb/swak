package io.neyb.swak.chain.route.requestUpdater

import io.neyb.swak.http.Request
import io.reactivex.Single

interface RequestUpdater {
    fun update(request:Request) : Single<Request>

    infix fun then(other: RequestUpdater) :RequestUpdater =
            ThenRequestUpdater(this, other)
}