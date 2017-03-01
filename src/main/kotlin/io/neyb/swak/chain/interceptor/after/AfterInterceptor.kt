package io.neyb.swak.chain.interceptor.before

import io.neyb.swak.http.Request
import io.neyb.swak.http.Response
import io.reactivex.Single

interface AfterInterceptor {
    fun onAfter(request: Request, response: Response): Single<Response>
}