package swak.server

import io.github.neyb.shoulk.hasMessage
import io.github.neyb.shoulk.shouldBe
import io.github.neyb.shoulk.shouldThrow
import io.github.neyb.shoulk.that
import io.reactivex.Single
import org.junit.jupiter.api.Test
import swak.config.configurer.SubRouteConfigurer
import swak.handler.Handler
import swak.http.request.Method
import swak.http.response.NotWritableResponse

class AbstractSwakServerTest {

    inner class TestSwakServer(conf: SubRouteConfigurer.() -> Unit) : AbstractSwakServer(conf) {
        var started = false
        var rootHandler: Handler<String>? = null

        override fun doStart(rootHandler: Handler<String>) {
            this.rootHandler = rootHandler
            started = true
        }

        override fun doStop() {
            this.rootHandler = null
            started = false
        }
    }

    val ss: TestSwakServer = TestSwakServer {
        handle("/", Method.GET) { Single.error<NotWritableResponse<Unit>>(IllegalStateException()) }
    }

    @Test fun `a new SwakServer should not be started`() {
        ss.started shouldBe false
    }

    @Test fun `a started SwakServer should call doStart`() {
        ss.start()
        ss.started shouldBe true
    }

    @Test fun `can start then stop a server`() {
        ss.start()
        ss.started shouldBe true
        ss.stop()
        ss.started shouldBe false
    }

    @Test fun `start a started server should fail`() {
        ss.start();
        { ss.start() } shouldThrow IllegalStateException::class that hasMessage("server already started!")
    }

    @Test fun `cannot stop a stopped server`() {
        { ss.stop() } shouldThrow IllegalStateException::class that hasMessage("server not started!")
    }
}