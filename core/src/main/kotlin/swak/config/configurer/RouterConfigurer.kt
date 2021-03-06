package swak.config.configurer

import io.reactivex.Single
import swak.http.request.Method
import swak.http.request.context.RequestContext
import swak.http.response.NotWritableResponse

interface RouterConfigurer : HandlerConfigurer {
    fun <IB, OB> handle(path: String, method: Method, reqBodyType: Class<IB>, respBodyType: Class<OB>, handler: (RequestContext<IB>) -> Single<out NotWritableResponse<OB>>)
    fun sub(path: String, configuration: SubRouteConfigurer.() -> Unit)
}