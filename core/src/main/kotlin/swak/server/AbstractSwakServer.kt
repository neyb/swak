package swak.server

import swak.config.configurer.SubRouteConfigurer
import swak.handler.Handler

abstract class AbstractSwakServer(
        mainConfiguration: SubRouteConfigurer.() -> Unit
) : SwakServer {

    private val rootHandler: Handler<String> = RootHandlerInitializer.initialise(mainConfiguration)
    private var started = false

    override final fun start() {
        if (started) throw IllegalStateException("server already started!")
        doStart(rootHandler)
        started = true
    }

    override final fun stop() {
        if (!started) throw IllegalStateException("server not started!")
        started = false
        doStop()
    }

    protected abstract fun doStart(rootHandler: Handler<String>)

    protected abstract fun doStop()

}

