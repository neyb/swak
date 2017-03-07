package io.neyb.swak.config.configuration

import io.neyb.swak.config.configurer.HandlerConfigurer

interface HandlerConfiguration<in C: HandlerConfigurer> {
    fun configure(configurer: C)
}