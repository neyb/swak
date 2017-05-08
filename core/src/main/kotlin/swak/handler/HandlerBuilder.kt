package swak.handler

internal interface HandlerBuilder<ReqBody, out RespBody> {
    fun build(): Handler<ReqBody>
}