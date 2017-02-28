package io.neyb.swak

import io.neyb.swak.chain.Chain
import io.neyb.swak.chain.ChainConfigurer
import io.neyb.swak.chain.route.interceptor.errorHandlers.NoRouteInterceptor
import io.neyb.swak.chain.route.interceptor.errorHandlers.SeveralRouteInterceptor
import io.undertow.Undertow

class SwakServer(
        private val configuration: Configuration = Configuration(),
        chainConfiguration: ChainConfigurer.() -> Unit = {}
) {
    private val chain: Chain = Chain().apply {
        ChainConfigurer(this).apply(chainConfiguration)
        interceptors.add(NoRouteInterceptor)
        interceptors.add(SeveralRouteInterceptor)
    }

    private val builder: Undertow.Builder = Undertow.builder()

    private var ut: Undertow? = null

    fun start() {
        if (ut != null) throw IllegalStateException("server already started!")

        ut = builder.addHttpListener(configuration.port, "0.0.0.0")
                .setHandler(ChainAdapterHttpHandler(chain))
                .build()

        ut!!.start()
    }

    fun stop() {
        (ut ?: throw IllegalStateException("server not started!"))
                .stop()
    }
}