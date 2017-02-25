package io.neyb.swak.core

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import rx.Observable
import kotlin.test.assertFailsWith

internal class SwakServerTest {

    val swakServer: SwakServer = SwakServer(Configuration(8080))
    val client: OkHttpClient = OkHttpClient()

    @AfterEach
    internal fun tearDown() {
        try {
            swakServer.stop()
        } catch (e: Exception) {
        }
    }

    @Test fun `a server can be started and stoped`() {
        swakServer.start()
        swakServer.stop()
    }

    @Test
    internal fun `a server already started cannot be started again`() {
        swakServer.start()
        assertFailsWith<IllegalStateException> { swakServer.start() }
    }

    @Test
    internal fun `a not started server cannot be stoped`() {
        assertFailsWith<IllegalStateException> { swakServer.stop() }
    }

    @Test fun `hello world server`() {
        swakServer.configureChain {
            get("/hello") { Observable.just(
                    io.neyb.swak.core.http.Response(body = "hello world!")) }
        }
        swakServer.start()

        val response = get("/hello")
        assert(response.isSuccessful, { "error while getting hello world" })
        assert(response.body().string() == "hello world!", { "body != hello world" })

        swakServer.stop()
    }

    @Test
    internal fun `get undefined path return 404`() {
        swakServer.start()
        val response = get("/hello")
        assert(response.code() == 404)
    }

    private fun get(path: String): Response =
            client.newCall(Request.Builder()
                    .url("http://localhost:8080" + path)
                    .get()
                    .build()).execute()


}