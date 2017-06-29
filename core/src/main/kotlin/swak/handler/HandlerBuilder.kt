package swak.handler

internal interface HandlerBuilder<ReqBody, out Out> {
    fun build(): Handler<ReqBody, Out>
}