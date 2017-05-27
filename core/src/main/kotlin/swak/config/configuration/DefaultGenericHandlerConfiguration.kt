package swak.config.configuration

import mu.KLoggable
import swak.body.reader.StringReader
import swak.body.reader.UnitReader
import swak.body.reader.provider.useAlways
import swak.body.writer.StringWriter
import swak.body.writer.UnitWriter
import swak.body.writer.provider.useAlways
import swak.config.configurer.SimpleAroundConfigurer
import swak.handler.router.NoRouteFound
import swak.handler.router.SeveralRouteFound
import swak.http.response.*

internal object DefaultGenericHandlerConfiguration : GenericHandlerConfiguration<SimpleAroundConfigurer>, KLoggable {

    override val logger = logger()

    override fun configure(configurer: SimpleAroundConfigurer) {
        configurer.apply {
            addContentReaderProvider(UnitReader.useAlways())
            addContentWriterProvider(UnitWriter.useAlways())
            addContentReaderProvider(StringReader.useAlways())
            addContentWriterProvider(StringWriter.useAlways())
            logAndRespondWithStatus<NoRouteFound>(Code.NOT_FOUND, logStack = false)
            logAndRespondWithStatus<SeveralRouteFound>(Code.INTERNAL_SERVER_ERROR, logStack = false)
            logAndRespondWithStatus<Throwable>(Code.INTERNAL_SERVER_ERROR, message = "an unhandled error occured:")
        }
    }

    inline fun <reified E : Throwable> SimpleAroundConfigurer.logAndRespondWithStatus(
            code: Code,
            message: String = "",
            logStack: Boolean = true) {
        handleError<E,  Unit> { error ->
            if (logStack) DefaultGenericHandlerConfiguration.logger.error(error) { message + error.message }
            else DefaultGenericHandlerConfiguration.logger.error { message + error.message }
            NoBodyResponse(status = code)
        }
    }
}

