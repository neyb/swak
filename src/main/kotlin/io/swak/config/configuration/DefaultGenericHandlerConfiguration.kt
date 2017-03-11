package io.swak.config.configuration

import io.swak.config.configurer.SimpleAroundConfigurer
import io.swak.handler.cross.NoRouteFound
import io.swak.handler.cross.SeveralRouteFound
import io.swak.http.Code
import io.swak.http.Response
import mu.KLoggable

internal object DefaultGenericHandlerConfiguration : GenericHandlerConfiguration<SimpleAroundConfigurer>, KLoggable {

    override val logger = logger()

    override fun configure(configurer: SimpleAroundConfigurer) {
        configurer.apply {
            logAndRespondWithStatus<NoRouteFound>(Code.NOT_FOUND, logStack = false)
            logAndRespondWithStatus<SeveralRouteFound>(Code.INTERNAL_SERVER_ERROR, logStack = false)
            logAndRespondWithStatus<Throwable>(Code.INTERNAL_SERVER_ERROR, message = "an unhandled error occured:")
            handleError(AnyErrorHandler)
            handleError(AnyErrorHandler)
        }
    }

    inline fun <reified E : Throwable> SimpleAroundConfigurer.logAndRespondWithStatus(
            code: Code,
            message: String = "",
            logStack: Boolean = true) {
        handleError<E> { error ->
            if (logStack) logger.error(error) { message + error.message }
            else logger.error { message + error.message }
            Response(status = code)
        }
    }
}

