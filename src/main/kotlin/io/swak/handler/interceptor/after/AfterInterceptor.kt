package io.swak.handler.interceptor.after

import io.swak.http.UpdatableRequest
import io.swak.http.Response
import io.reactivex.Single

interface AfterInterceptor<in B> {
    fun <B> onAfter(request: UpdatableRequest<B>, response: Response): Single<Response>
}