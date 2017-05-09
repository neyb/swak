package swak.config.configuration

import swak.config.configurer.HandlerConfigurer

interface GenericHandlerConfiguration<in C: HandlerConfigurer> {
    fun configure(configurer: C)
}