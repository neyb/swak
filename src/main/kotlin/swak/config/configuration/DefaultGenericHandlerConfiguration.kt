package swak.config.configuration

import mu.KLoggable
import swak.config.configurer.SimpleAroundConfigurer
import swak.handler.cross.NoRouteFound
import swak.handler.cross.SeveralRouteFound
import swak.http.Code
import swak.http.Response

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
            if (logStack) DefaultGenericHandlerConfiguration.logger.error(error) { message + error.message }
            else DefaultGenericHandlerConfiguration.logger.error { message + error.message }
            Response(status = code)
        }
    }
}

