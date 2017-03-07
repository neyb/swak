package io.neyb.swak.handler.config.configuration

import io.neyb.swak.handler.config.configurable.HandlerConfigurer
import io.neyb.swak.handler.cross.NoRouteFound
import io.neyb.swak.handler.cross.SeveralRouteFound
import io.neyb.swak.http.Code
import io.neyb.swak.http.Response
import mu.KLoggable

object DefaultHandlerConfiguration : HandlerConfiguration<HandlerConfigurer>, KLoggable {
    override val logger = logger()

    override fun configure(configurer: HandlerConfigurer) {
        configurer.apply {
            logAndRespondWithStatus<NoRouteFound>(Code.NOT_FOUND, logStack = false)
            logAndRespondWithStatus<SeveralRouteFound>(Code.INTERNAL_SERVER_ERROR, logStack = false)
            logAndRespondWithStatus<Throwable>(Code.INTERNAL_SERVER_ERROR, message = "an unhandled error occured:")

//            addContentReaderProvider(StringContentReader)
//            chain.errorHandlers.add(NoRouteInterceptor)
//            chain.errorHandlers.add(SeveralRouteInterceptor)
//            addContentReaderProvider(StringContentReader)
        }
    }

    inline fun <reified E : Throwable> HandlerConfigurer.logAndRespondWithStatus(
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

