package io.neyb.swak.route.interceptors.after

import io.neyb.swak.http.Request
import io.neyb.swak.http.Response
import io.reactivex.Single

interface AfterInterceptor<in B> {
    fun <B> onAfter(request: Request<B>, response: Response): Single<Response>
}