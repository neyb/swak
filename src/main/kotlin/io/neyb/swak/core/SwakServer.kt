package io.neyb.swak.core

import io.neyb.swak.core.chain.Chain
import io.neyb.swak.core.chain.ChainConfigurer
import io.undertow.Undertow

class SwakServer(
        private val configuration: Configuration = Configuration(),
        chainConfiguration: ChainConfigurer.() -> Unit = {}
) {
    private val chain: Chain = Chain().apply { ChainConfigurer(this).apply(chainConfiguration) }
    private val builder: Undertow.Builder = Undertow.builder()

    private var ut: Undertow? = null

    fun configureChain(chainConfiguration: ChainConfigurer.() -> Unit) {
        chain.apply { ChainConfigurer(this).apply(chainConfiguration) }
    }

    fun start() {
        if (ut != null) throw IllegalStateException("server started!")

        ut = builder.addHttpListener(configuration.port, "0.0.0.0")
                .setHandler(ChainAdapterHttpHandler(chain))
                .build()

        ut!!.start()
    }

//    fun addRoute(s: String, function: () -> String) {}

    fun stop() {
        (ut ?: throw IllegalStateException("server not started!"))
                .stop()
    }
}

