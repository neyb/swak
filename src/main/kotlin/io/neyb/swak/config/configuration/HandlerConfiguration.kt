package io.neyb.swak.config.configuration

import io.neyb.swak.config.configurable.HandlerConfigurer

interface HandlerConfiguration<in C: HandlerConfigurer> {
    fun configure(configurer: C)
}