package io.swak.config.configuration

import io.swak.config.configurer.HandlerConfigurer

interface GenericHandlerConfiguration<in C: HandlerConfigurer> {
    fun configure(configurer: C)
}