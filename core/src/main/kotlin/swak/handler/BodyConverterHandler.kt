package swak.handler

import swak.body.reader.provider.request.BodyReaderChooser
import swak.body.writer.provider.request.BodyWriterChooser
import swak.handler.NotWritable.NotWritableHandler
import swak.http.request.context.UpdatableRequestContext
import swak.http.response.context.UpdatableResponseContext

internal class BodyConverterHandler<ReqBody, out RespBody>(
        private val readerProvider: BodyReaderChooser<ReqBody>,
        private val handler: NotWritableHandler<ReqBody, RespBody>,
        private val writerChooser: BodyWriterChooser<RespBody>
) : Handler<String, RespBody> {
    override suspend fun handle(reqContext: UpdatableRequestContext<String>): UpdatableResponseContext<String, RespBody> {
        val request = reqContext.request
        val responseContext = handler.handle(reqContext.withBodyReader(readerProvider.forRequest(request)))
        return responseContext
                .withWriter(writerChooser.`for`(responseContext.response, request))
                .withRequestContext(reqContext)
    }
}