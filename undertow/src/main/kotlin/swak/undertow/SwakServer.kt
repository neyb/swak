package swak.undertow

import io.undertow.Undertow
import swak.server.Configuration
import swak.config.configurer.SubRouteConfigurer
import swak.handler.Handler
import swak.server.AbstractSwakServer
import swak.undertow.RouteAdapterHttpHandler

class SwakServer(
        serverConfiguration: Configuration = Configuration(),
        mainConfiguration: SubRouteConfigurer.() -> Unit = {}
) : AbstractSwakServer(serverConfiguration, mainConfiguration) {

    private var ut: Undertow? = null

    override fun start(rootHandler: Handler<String>) {
        ut = Undertow.builder().addHttpListener(serverConfiguration.port, "0.0.0.0")
                .setHandler(RouteAdapterHttpHandler(rootHandler))
                .build()
        ut!!.start()
    }

    override fun safeStop() = ut!!.stop()
}