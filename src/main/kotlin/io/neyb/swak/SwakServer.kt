package io.neyb.swak

import io.neyb.swak.chain.Chain
import io.neyb.swak.chain.ChainConfigurer
import io.neyb.swak.chain.contentReader.StringContentReader
import io.neyb.swak.chain.interceptor.errorHandler.NoRouteInterceptor
import io.neyb.swak.chain.interceptor.errorHandler.SeveralRouteInterceptor
import io.undertow.Undertow
import java.util.*

class SwakServer(
        private val configuration: Configuration = Configuration(),
        chainConfiguration: ChainConfigurer.() -> Unit = {}
) {
    private val chain: Chain = Chain().apply {
        errorHandlers.add(NoRouteInterceptor)
        errorHandlers.add(SeveralRouteInterceptor)
        val chainConfigurer = ChainConfigurer(this)
        chainConfigurer.addContentReaderProvider(StringContentReader)
        chainConfigurer.apply(chainConfiguration)
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