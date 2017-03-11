package swak.handler

internal interface HandlerBuilder<B> {
    fun build(): Handler<B>
}