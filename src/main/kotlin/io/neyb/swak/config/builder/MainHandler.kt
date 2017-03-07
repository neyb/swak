package io.neyb.swak.config.builder

class MainHandler(
        parent: ConfigurableHandler? = null
) : ConfigurableHandler(parent) {
    public override val innerHandlerBuilder = ConfigurableCrossHandler(this)
}