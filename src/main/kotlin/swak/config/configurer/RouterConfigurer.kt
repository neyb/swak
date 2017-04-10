package swak.config.configurer

import io.reactivex.Single
import swak.http.request.Method
import swak.http.request.Request
import swak.http.response.NotWritableResponse
import swak.http.response.SimpleResponse

interface RouterConfigurer : HandlerConfigurer {
    fun <OB : Any> handle(path: String, method: Method, respBodyType: Class<OB>, handler: (Request<String>) -> Single<out NotWritableResponse<OB>>)
    fun <IB, OB : Any> handleTyped(path: String, method: Method, reqBodyType: Class<IB>, respBodyType: Class<OB>, handler: (Request<IB>) -> Single<out NotWritableResponse<OB>>)
    fun sub(path: String, configuration: SubRouteConfigurer.() -> Unit)
}