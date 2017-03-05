package io.neyb.swak.route.configuration

import io.neyb.swak.route.interceptors.before.reader.StringContentReader
import io.neyb.swak.http.Code
import io.neyb.swak.http.Response
import io.neyb.swak.route.*
import mu.KLoggable

object DefaultRouteConfiguration : RouteConfiguration<String, String>, KLoggable {
    override val logger = logger()

    override fun invoke(subRouteConfigurer: SubRouteConfigurer<String, String>) {
        subRouteConfigurer.apply {
            logAndRespondWithStatus<NoRouteFound>(Code.NOT_FOUND, logStack = false)
            logAndRespondWithStatus<SeveralRouteFound>(Code.INTERNAL_SERVER_ERROR, logStack = false)
            logAndRespondWithStatus<Throwable>(Code.INTERNAL_SERVER_ERROR, message = "an unhandled error occured:")

            addContentReaderProvider(StringContentReader)
//            chain.errorHandlers.add(NoRouteInterceptor)
//            chain.errorHandlers.add(SeveralRouteInterceptor)
//            addContentReaderProvider(StringContentReader)
        }
    }

    inline fun <reified E : Throwable> SubRouteConfigurer<*, *>.logAndRespondWithStatus(
            code: Code,
            message: String = "",
            logStack: Boolean = true) {
        addErrorHandler<E> { error ->
            if (logStack) logger.error(error) { message + error.message }
            else logger.error { message + error.message }
            Response(status = code)
        }
    }
}

