package swak

import io.undertow.Undertow
import swak.config.configurable.SimpleConfigurableAround
import swak.config.configurable.SubRouteConfigurable
import swak.config.configuration.DefaultGenericHandlerConfiguration
import swak.config.configurer.SimpleAroundConfigurer
import swak.config.configurer.SubRouteConfigurer
import swak.handler.Handler

class SwakServer(
        private val serverConfiguration: Configuration = Configuration(),
        mainConfiguration: SubRouteConfigurer.() -> Unit = {}
) {
    private val rootHandler: Handler<String>

    init {
        val rootHandlerBuilder = SimpleConfigurableAround()
        val mainHandlerBuilder = SubRouteConfigurable(rootHandlerBuilder)
        rootHandlerBuilder.interceptHandlerBuilder.innerHandler = mainHandlerBuilder

        DefaultGenericHandlerConfiguration.configure(SimpleAroundConfigurer(rootHandlerBuilder))
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