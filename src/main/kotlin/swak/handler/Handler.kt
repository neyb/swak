package swak.handler

import io.reactivex.Single
import swak.http.Response
import swak.http.UpdatableRequest

interface Handler<B> {
    fun handle(request: UpdatableRequest<B>): Single<Response>
}