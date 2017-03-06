package io.neyb.swak.handler.config.configuration

import io.neyb.swak.handler.config.builder.ConfigurableHandler
import io.neyb.swak.handler.config.configurable.HandlerConfigurer

interface HandlerConfiguration<in C: HandlerConfigurer> {
    fun configure(configurer: C)
}