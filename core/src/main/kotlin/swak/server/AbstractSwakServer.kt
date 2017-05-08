package swak.server

import swak.config.configurable.SimpleConfigurableAround
import swak.config.configurable.SubRouteConfigurable
import swak.config.configuration.DefaultGenericHandlerConfiguration
import swak.config.configurer.SimpleAroundConfigurer
import swak.config.configurer.SubRouteConfigurer
import swak.handler.Handler
abstract class AbstractSwakServer(
        protected val serverConfiguration: Configuration = Configuration(),
        mainConfiguration: SubRouteConfigurer.() -> Unit = {}
) : SwakServer {

    private val rootHandler: Handler<String>
    private var started = false

    init {
        val rootHandlerBuilder = SimpleConfigurableAround()
        val mainHandlerBuilder = SubRouteConfigurable(rootHandlerBuilder)
        rootHandlerBuilder.interceptHandlerBuilder.innerHandler = mainHandlerBuilder

        DefaultGenericHandlerConfiguration.configure(SimpleAroundConfigurer(rootHandlerBuilder))
        mainConfiguration(SubRouteConfigurer(mainHandlerBuilder))

        rootHandler = rootHandlerBuilder.build()
    }

    override final fun start() {
        if(started) throw IllegalStateException("server already started!")
        start(rootHandler)
        started = true
    }
    protected abstract fun start(rootHandler: Handler<String>)

    override final fun stop() {
        if(!started) throw IllegalStateException("server not started!")
        started = false
        safeStop()
    }
    abstract fun safeStop()

}

