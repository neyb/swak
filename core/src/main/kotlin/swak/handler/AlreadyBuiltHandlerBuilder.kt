package swak.handler

class AlreadyBuiltHandlerBuilder<IB>(private val handler: Handler<IB>) : HandlerBuilder<IB> {
    override fun build() = handler
}