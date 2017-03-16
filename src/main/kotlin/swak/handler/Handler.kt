package swak.handler

import io.reactivex.Single
import swak.http.response.Response
import swak.http.request.UpdatableRequest

interface Handler<B> {
    fun handle(request: UpdatableRequest<B>): Single<Response>
}