package io.neyb.swak

import io.neyb.swak.route.*
import io.neyb.swak.route.configuration.DefaultRouteConfiguration
import io.undertow.Undertow

class SwakServer(
        private val configuration: Configuration = Configuration(),
        routeCongurer: SubRouteConfigurer<String, String>.() -> Unit = {}
) {
    private val mainRoute: SubRoute<String, String>

    init {
        val mainRouteBuilder = SubRouteBuilder("", String::class.java, String::class.java, null, "main")
        val mainRouteConfigurer = SubRouteConfigurer(mainRouteBuilder, String::class.java)
        DefaultRouteConfiguration(mainRouteConfigurer)

        val userRouteBuilder = SubRouteBuilder("", String::class.java, String::class.java, mainRouteBuilder.contentReaderProviders, "user")
        val userRouteConfigurer = SubRouteConfigurer(userRouteBuilder, String::class.java)
        routeCongurer(userRouteConfigurer)

        mainRouteBuilder.routes.add(userRouteBuilder.build())

        mainRoute = mainRouteBuilder.build()
    }

    private val builder: Undertow.Builder = Undertow.builder()

    private var ut: Undertow? = null

    fun start() {
        if (ut != null) throw IllegalStateException("server already started!")

        ut = builder.addHttpListener(configuration.port, "0.0.0.0")
                .setHandler(RouteAdapterHttpHandler(mainRoute))
                .build()

        ut!!.start()
    }

    fun stop() {
        (ut ?: throw IllegalStateException("server not started!"))
                .stop()
    }
}