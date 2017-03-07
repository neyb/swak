package io.neyb.swak.handler.config.builder

class MainHandler(
        parent: ConfigurableHandler? = null
) : ConfigurableHandler(parent) {
    public override val innerHandlerBuilder = ConfigurableCrossHandler(this)
}