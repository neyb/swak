package swak.undertow

import io.undertow.Undertow
import swak.config.configurer.SubRouteConfigurer
import swak.server.*

class SwakServer(
        private val serverConfiguration: Configuration = Configuration(),
        mainConfiguration: SubRouteConfigurer.() -> Unit = {}
) : AbstractSwakServer(mainConfiguration) {

    private var ut: Undertow? = null

    override fun doStart(rootHandler: RootReqHandler) {
        ut = Undertow.builder()
                .addHttpListener(serverConfiguration.port, "0.0.0.0")
                .setHandler(RouteAdapterHttpHandler(rootHandler))
                .build()
        ut!!.start()
    }

    override fun doStop() = ut!!.stop()
}