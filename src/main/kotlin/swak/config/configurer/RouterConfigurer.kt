package swak.config.configurer

import io.reactivex.Single
import swak.http.*

interface RouterConfigurer : HandlerConfigurer {
//    val routerHandlerBuilder: Router.Builder
//    fun addRoute(route: Route)
    fun handle(path: String, method: Method, handler: (Request<String>) -> Single<Response>)
    fun <B> handleTyped(path: String, method: Method, bodyType: Class<B>, handler: (Request<B>) -> Single<Response>)
    fun sub(path: String, configuration: SubRouteConfigurer.() -> Unit)
}