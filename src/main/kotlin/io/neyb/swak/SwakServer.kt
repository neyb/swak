package io.neyb.swak

import io.neyb.swak.config.configurable.SimpleConfigurableIntercepter
import io.neyb.swak.config.configurable.SubRouteConfigurable
import io.neyb.swak.config.configuration.DefaultHandlerConfiguration
import io.neyb.swak.config.configurer.SimpleIntercepterConfigurer
import io.neyb.swak.config.configurer.SubRouteConfigurer
import io.neyb.swak.handler.Handler
import io.undertow.Undertow

class SwakServer(
        private val serverConfiguration: Configuration = Configuration(),
        mainConfiguration: SubRouteConfigurer.() -> Unit = {}
) {
    private val rootHandler: Handler<String>

    init {
        val rootHandlerBuilder = SimpleConfigurableIntercepter()
        val rootHandlerConfigurer =SimpleIntercepterConfigurer(rootHandlerBuilder)
        DefaultHandlerConfiguration.configure(rootHandlerConfigurer)

        val mainHandlerBuilder = SubRouteConfigurable(rootHandlerBuilder)
        rootHandlerBuilder.innerHandlerBuilder = mainHandlerBuilder
        mainConfiguration(SubRouteConfigurer(mainHandlerBuilder))

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