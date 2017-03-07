package io.neyb.swak.config.configuration

import io.neyb.swak.config.configurer.SimpleIntercepterConfigurer
import io.neyb.swak.handler.cross.NoRouteFound
import io.neyb.swak.handler.cross.SeveralRouteFound
import io.neyb.swak.http.Code
import io.neyb.swak.http.Response
import mu.KLoggable

object DefaultHandlerConfiguration : HandlerConfiguration<SimpleIntercepterConfigurer>, KLoggable {

    override val logger = logger()

    override fun configure(configurer: SimpleIntercepterConfigurer) {
        configurer.apply {
            logAndRespondWithStatus<NoRouteFound>(Code.NOT_FOUND, logStack = false)
            logAndRespondWithStatus<SeveralRouteFound>(Code.INTERNAL_SERVER_ERROR, logStack = false)
            logAndRespondWithStatus<Throwable>(Code.INTERNAL_SERVER_ERROR, message = "an unhandled error occured:")
        }
    }

    inline fun <reified E : Throwable> SimpleIntercepterConfigurer.logAndRespondWithStatus(
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

