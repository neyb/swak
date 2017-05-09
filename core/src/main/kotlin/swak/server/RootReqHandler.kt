package swak.server

import swak.body.reader.StringReader
import swak.handler.Handler
import swak.http.request.*
import swak.http.request.context.UpdatableRequestContext

class RootReqHandler(private val rootHandler: Handler<String>) {
    fun handle(request: BasicRequest) = rootHandler
            .handle(UpdatableRequestContext(UpdatableRequest(request, StringReader)))
            .map { it.response }
}