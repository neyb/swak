package swak.server

import io.github.neyb.shoulk.*
import io.reactivex.Single
import org.junit.Test
import swak.http.request.Method.GET
import swak.http.response.NotWritableResponse

class SwakServerTest {

    class TestEngine : SwakServerEngine {
        var started = false
        var rootHandler: RootReqHandler? = null

        override fun start(rootHandler: RootReqHandler) {
            this.rootHandler = rootHandler
            started = true
        }

        override fun stop() {
            this.rootHandler = null
            started = false
        }
    }

    val engine = TestEngine()

    val ss: SwakServer = SwakServer(engine) {
        on(".*", GET) answer { Single.error<NotWritableResponse<Unit>>(IllegalStateException()) }
    }

    @Test fun `a new SwakServer should not be started`() {
        engine.started shouldBe false
    }

    @Test fun `a started SwakServer should call doStart`() {
        ss.start()
        engine.started shouldBe true
    }

    @Test fun `can start then stop a server`() {
        ss.start()
        engine.started shouldBe true
        ss.stop()
        engine.started shouldBe false
    }

    @Test fun `start a started server should fail`() {
        ss.start();
        { ss.start() } shouldThrow IllegalStateException::class that hasMessage("server already started!")
    }

    @Test fun `cannot stop a stopped server`() {
        { ss.stop() } shouldThrow IllegalStateException::class that hasMessage("server not started!")
    }
}