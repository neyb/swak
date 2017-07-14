package swak.handler

class AlreadyBuiltHandlerBuilder<IB, out OB>(private val handler: Handler<IB, OB>) : HandlerBuilder<IB, OB> {
    override fun build() = handler
}