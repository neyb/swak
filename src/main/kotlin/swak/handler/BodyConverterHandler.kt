package swak.handler

import io.reactivex.Single
import swak.http.requestContext.UpdatableRequestContext
import swak.http.requestContext.UpdatableResponseContext
import swak.reader.provider.request.BodyReaderRequestProvider

internal class BodyConverterHandler<Body>(
        private val readerProvider: BodyReaderRequestProvider<Body>,
        private val handler: Handler<Body>
) : Handler<String> {
    override fun handle(reqContext: UpdatableRequestContext<String>): Single<UpdatableResponseContext<String>> {
        val request = reqContext.request
        return handler.handle(reqContext.withBodyReader(readerProvider.forRequest(request)))
                .map { UpdatableResponseContext(
                        UpdatableRequestContext(request),
                        it.response) }
    }
}