package io.neyb.swak

import io.neyb.swak.handler.Handler
import io.neyb.swak.config.builder.MainHandler
import io.neyb.swak.config.configurable.CrossConfigurer
import io.neyb.swak.config.configurable.HandlerConfigurer
import io.neyb.swak.config.configuration.DefaultHandlerConfiguration
import io.undertow.Undertow

class SwakServer(
        private val configuration: Configuration = Configuration(),
        crossConfiguration: CrossConfigurer.() -> Unit = {}
) {
    private val mainHandler: Handler<String>

    init {
        val mainHandlerBuilder = MainHandler()
        val mainHandlerConfigurer = HandlerConfigurer(mainHandlerBuilder)
        DefaultHandlerConfiguration.configure(mainHandlerConfigurer)

        val userHandlerBuilder = mainHandlerBuilder.innerHandlerBuilder
        CrossConfigurer(userHandlerBuilder).crossConfiguration()

        mainHandler = mainHandlerBuilder.build()
    }

    private val builder: Undertow.Builder = Undertow.builder()

    private var ut: Undertow? = null

    fun start() {
        if (ut != null) throw IllegalStateException("server already started!")

        ut = builder.addHttpListener(configuration.port, "0.0.0.0")
                .setHandler(RouteAdapterHttpHandler(mainHandler))
                .build()

        ut!!.start()
    }

    fun stop() {
        (ut ?: throw IllegalStateException("server not started!"))
                .stop()
    }
}