package swak.handler

class AlreadyBuiltHandlerBuilder<B>(private val handler: Handler<B>) : HandlerBuilder<B> {
    override fun build() = handler
}