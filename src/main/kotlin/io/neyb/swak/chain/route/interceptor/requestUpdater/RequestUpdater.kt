package io.neyb.swak.chain.route.interceptor.requestUpdater

import io.neyb.swak.http.Request

interface RequestUpdater {
    fun update(request:Request) : Request

    infix fun then(other: RequestUpdater) :RequestUpdater =
            ThenRequestUpdater(this, other)
}