package swak

import io.github.neyb.shoulk.*
import io.github.neyb.shoulk.matcher.*
import io.reactivex.Single
import org.junit.jupiter.api.Test
import swak.http.request.Method
import swak.http.response.NoBodyResponse

class SwakServer_basicsTest : SwakServerTest() {

    @Test fun `a server can be started and stoped`() {
        val swakServer = swakServer {
            handle("/", Method.GET) { Single.just(NoBodyResponse())}
        }
        swakServer.start()
        swakServer.stop()
    }

    @Test
    internal fun `a server already started cannot be started again`() {
        swakServer {
            handle("/", Method.GET) { Single.just(NoBodyResponse()) }
        }.start();
        { swakServer.start() } shouldThrow IllegalStateException::class that hasMessage("server already started!")
    }

    @Test
    internal fun `a not started server cannot be stoped`() {
        { swakServer {
            handle("/", Method.GET) { Single.just(NoBodyResponse()) }
        }.stop() } shouldThrow IllegalStateException::class that hasMessage("server not started!")
    }

    @Test
    internal fun `get undefined path return 404`() {
        swakServer {
            handle("/", Method.GET) { Single.just(NoBodyResponse()) }
        }.start()
        get(path = "/hello", checkSuccess = false) should match("fail with 404") { it.code() == 404 }
    }
}