package io.neyb.swak.config.builder

import io.neyb.swak.handler.cross.Cross
import io.neyb.swak.handler.cross.route.Route

class ConfigurableCrossHandler(
        parent: ConfigurableHandler? = null
) : ConfigurableHandler(parent) {
    override val innerHandlerBuilder = Cross.Builder()

    fun addRoute(route: Route<String>) {
        innerHandlerBuilder.routes.add(route)
    }

}