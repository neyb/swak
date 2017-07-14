package swak

import io.github.neyb.shoulk.*
import io.github.neyb.shoulk.matcher.match
import io.reactivex.Single
import org.junit.Test
import swak.http.request.Method.GET
import swak.http.response.*

class SwakServer_basicsTest : SwakServerTest() {

    @Test fun `a server can be started and stoped`() {
        val swakServer = swakServer {
            on("/", GET) answer { Single.just(SimpleResponse.withoutBody()) }
        }
        swakServer.start()
        swakServer.stop()
    }

    @Test
    internal fun `a server already started cannot be started again`() {
        swakServer {
            on("/", GET) answer { Single.just(SimpleResponse.withoutBody()) }
        }.start();
        { swakServer.start() } shouldThrow IllegalStateException::class that hasMessage("server already started!")
    }

    @Test
    internal fun `a not started server cannot be stopped`() {
        { swakServer {
            on("/", GET) answer { Single.just(SimpleResponse.withoutBody()) }
        }.stop() } shouldThrow IllegalStateException::class that hasMessage("server not started!")
    }

    @Test
    internal fun `get undefined path return 404`() {
        swakServer {
            on("/", GET) answer { Single.just(SimpleResponse.withoutBody()) }
        }.start()
        get(path = "/hello", checkSuccess = false) should match("fail with 404") { it.code() == 404 }
    }
}