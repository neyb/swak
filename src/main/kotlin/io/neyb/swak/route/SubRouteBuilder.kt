package io.neyb.swak.route

import io.neyb.swak.route.interceptors.after.AfterInterceptors
import io.neyb.swak.route.matcher.AlwaysMatcher
import io.neyb.swak.route.matcher.RequestMatcher
import io.neyb.swak.route.interceptors.before.reader.*
import io.neyb.swak.route.interceptors.errorHandler.ErrorHandler
import io.neyb.swak.route.interceptors.errorHandler.ErrorHandlers
import java.util.*

class SubRouteBuilder<OuterBody, InnerBody>(
        val pathPrefix: String = "",
        val outerBody: Class<OuterBody>,
        val innerBody: Class<InnerBody>,
        parent: ContentReaderProviders?,
        val name: String = "generic"
) {
    val contentReaderProviders = ContentReaderProviders(parent)

    val routes: MutableList<Route<InnerBody>> = ArrayList()

    var requestMatcher: RequestMatcher<OuterBody>? = null
    var beforeRouteInterceptor: BeforeRouteInterceptor<OuterBody, InnerBody>? = null
    val errorhandlers = ErrorHandlers()
    var afterRouteInterceptor: AfterInterceptors<OuterBody> = AfterInterceptors()

    fun addErrorhandler(errorHandler: ErrorHandler) {
        errorhandlers.add(errorHandler)
    }

    fun build(): SubRoute<OuterBody, InnerBody> {
        return SubRoute(
                requestMatcher ?: AlwaysMatcher(),
                beforeRouteInterceptor ?:
                        BeforeRouteInterceptor(
//                                preContentNegociationInterceptor = PathParamExtractor<OuterBody>(routePath),
                                requestContentReaders = contentReaderProviders
                                        .forClass(outerBody, innerBody)
                                        ?: throw IllegalStateException("no requestContentInterceptor")//TODO
                        ),
                if (routes.size == 1) routes[0]
                else Routes(routes), //FIXME add routes
                afterRouteInterceptor,
                errorhandlers)
    }

    fun addContentReaderProvider(contentReaderProvider: RequestContentReaderProvider) {
        contentReaderProviders.add(contentReaderProvider)
    }
}