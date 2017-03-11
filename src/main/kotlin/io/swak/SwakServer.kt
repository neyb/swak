package io.swak

import io.swak.config.configurable.SimpleConfigurableAround
import io.swak.config.configurable.SubRouteConfigurable
import io.swak.config.configuration.DefaultGenericHandlerConfiguration
import io.swak.config.configurer.SimpleAroundConfigurer
import io.swak.config.configurer.SubRouteConfigurer
import io.swak.handler.Handler
import io.undertow.Undertow

class SwakServer(
        private val serverConfiguration: Configuration = Configuration(),
        mainConfiguration: SubRouteConfigurer.() -> Unit = {}
) {
    private val rootHandler: Handler<String>

    init {
        val rootHandlerBuilder = SimpleConfigurableAround()
        DefaultGenericHandlerConfiguration.configure(SimpleAroundConfigurer(rootHandlerBuilder))

        val mainHandlerBuilder = SubRouteConfigurable(rootHandlerBuilder)
        mainConfiguration(SubRouteConfigurer(mainHandlerBuilder))

        rootHandlerBuilder.innerHandlerBuilder = mainHandlerBuilder

        rootHandler = rootHandlerBuilder.build()
    }

    private val builder: Undertow.Builder = Undertow.builder()

    private var ut: Undertow? = null

    fun start() {
        if (ut != null) throw IllegalStateException("server already started!")

        ut = builder.addHttpListener(serverConfiguration.port, "0.0.0.0")
                .setHandler(RouteAdapterHttpHandler(rootHandler))
                .build()

        ut!!.start()
    }

    fun stop() {
        (ut ?: throw IllegalStateException("server not started!"))
                .stop()
    }
}