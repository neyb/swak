package swak.interceptor.after

import io.reactivex.Single
import swak.http.request.Request
import swak.http.requestContext.UpdatableResponseContext
import swak.http.response.Response

//FIXME shitty generics in this class
interface ResponseUpdater<B> : AfterInterceptor<B> {
    override fun updateRequestContext(respContext: UpdatableResponseContext<B>) =
            onAfter(respContext.request, respContext.response)
                    .map { respContext.copy(response = it) }!!

    fun <B> onAfter(request: Request<B>, response: Response): Single<Response>
}