package swak.undertow

import io.undertow.Undertow
import swak.server.*

class UndertowEngine(
        private val serverConfiguration: Configuration = Configuration()
) : SwakServerEngine {

    private var ut: Undertow? = null

    override fun start(rootHandler: RootReqHandler) {
        ut = Undertow.builder()
                .addHttpListener(serverConfiguration.port, "0.0.0.0")
                .setHandler(RouteAdapterHttpHandler(rootHandler))
                .build()
        ut!!.start()
    }

    override fun stop() = ut!!.stop()
}