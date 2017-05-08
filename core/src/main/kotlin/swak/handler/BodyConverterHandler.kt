package swak.handler

import io.reactivex.Single
import swak.body.reader.provider.request.BodyReaderChooser
import swak.body.writer.provider.request.BodyWriterChooser
import swak.handler.NotWritable.NotWritableHandler
import swak.http.request.context.UpdatableRequestContext
import swak.http.response.context.UpdatableResponseContext

internal class BodyConverterHandler<ReqBody, RespBody : Any>(
        private val readerProvider: BodyReaderChooser<ReqBody>,
        private val handler: NotWritableHandler<ReqBody, RespBody>,
        private val writerChooser: BodyWriterChooser<RespBody>
) : Handler<String> {
    override fun handle(reqContext: UpdatableRequestContext<String>): Single<UpdatableResponseContext<String>> {
        val request = reqContext.request
        return handler.handle(reqContext.withBodyReader(readerProvider.forRequest(request)))
                .map {
                    it
                            .withWriter(writerChooser.`for`(it.response, request))
                            .withRequestContext(reqContext)
                }
    }
}