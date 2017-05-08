package swak.server

import swak.config.configurable.SimpleConfigurableAround
import swak.config.configurable.SubRouteConfigurable
import swak.config.configuration.DefaultGenericHandlerConfiguration
import swak.config.configurer.SimpleAroundConfigurer
import swak.config.configurer.SubRouteConfigurer
import swak.handler.Handler

object RootHandlerInitializer {
    fun initialise(mainConfiguration: SubRouteConfigurer.() -> Unit): Handler<String> {
        val rootHandlerBuilder = SimpleConfigurableAround()
        val mainHandlerBuilder = SubRouteConfigurable(rootHandlerBuilder)
        rootHandlerBuilder.interceptHandlerBuilder.innerHandler = mainHandlerBuilder

        DefaultGenericHandlerConfiguration.configure(SimpleAroundConfigurer(rootHandlerBuilder))
        mainConfiguration(SubRouteConfigurer(mainHandlerBuilder))

        return  rootHandlerBuilder.build()
    }

}