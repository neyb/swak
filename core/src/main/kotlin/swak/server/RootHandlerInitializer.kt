package swak.server

import swak.config.configurable.SimpleConfigurableAround
import swak.config.configurable.SubRouteConfigurable
import swak.config.configuration.DefaultGenericHandlerConfiguration
import swak.config.configurer.SimpleAroundConfigurer
import swak.config.configurer.SubRouteConfigurer

internal object RootHandlerInitializer {
    fun initialise(mainConfiguration: SubRouteConfigurer.() -> Unit): RootReqHandler {
        val rootHandlerBuilder = SimpleConfigurableAround()
        val mainHandlerBuilder = SubRouteConfigurable(rootHandlerBuilder)
        rootHandlerBuilder.interceptHandlerBuilder.innerHandler = mainHandlerBuilder

        DefaultGenericHandlerConfiguration.configure(SimpleAroundConfigurer(rootHandlerBuilder))
        mainConfiguration(SubRouteConfigurer(mainHandlerBuilder))

        return RootReqHandler(rootHandlerBuilder.build())
    }

}