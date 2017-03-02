package io.neyb.swak

import io.github.neyb.shoulk.*
import io.github.neyb.shoulk.matcher.*
import io.neyb.swak.chain.ChainConfigurer
import okhttp3.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Tag

@Tag("it")
open class SwakServerTest {
    val client: OkHttpClient = OkHttpClient()
    var swakServer = swakServer()
        private set

    fun swakServer(configuration: Configuration = Configuration(8080), chainConfiguration: ChainConfigurer.() -> Unit = {}): SwakServer {
        swakServer = SwakServer(configuration, chainConfiguration)
        return swakServer
    }

    @AfterEach
    internal fun tearDown() {
        try {
            swakServer.stop()
        } catch (e: Exception) {
        }
    }

    fun post(path: String, body: String, checkSuccess: Boolean = true) =
            send(path, checkSuccess) { it.post(RequestBody.create(MediaType.parse("plain/text"), body)) }

    fun get(path: String, checkSuccess: Boolean = true): Response = send(path, checkSuccess) { it.get() }

    fun send(path: String, checkSuccess: Boolean = true, build: (Request.Builder) -> Unit): Response =
            client.newCall(Request.Builder()
                    .url("http://localhost:8080" + path)
                    .apply(build)
                    .build()).execute()
                    .apply {
                        if (checkSuccess)
                            this should match("is successfull") { it.isSuccessful }
                    }
}