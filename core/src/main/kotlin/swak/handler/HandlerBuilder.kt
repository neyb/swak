package swak.handler

internal interface HandlerBuilder<ReqBody> {
    fun build(): Handler<ReqBody>
}