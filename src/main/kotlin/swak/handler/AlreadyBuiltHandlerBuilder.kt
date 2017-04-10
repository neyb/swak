package swak.handler

class AlreadyBuiltHandlerBuilder<IB, out OB>(private val handler: Handler<IB>) : HandlerBuilder<IB, OB> {
    override fun build() = handler
}