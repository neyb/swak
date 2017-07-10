package swak.server

import swak.config.configurer.SubRouteConfigurer

class SwakServer(
        private val swakServerEngine: SwakServerEngine,
        mainConfiguration: SubRouteConfigurer.() -> Unit
) {

    private val rootHandler: RootReqHandler = RootHandlerInitializer.initialise(mainConfiguration)
    private var started = false

    fun start() {
        if (started) throw IllegalStateException("server already started!")
        swakServerEngine.start(rootHandler)
        started = true
    }

    fun stop() {
        if (!started) throw IllegalStateException("server not started!")
        started = false
        swakServerEngine.stop()
    }
}

