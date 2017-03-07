package io.neyb.swak

import io.github.neyb.shoulk.*
import io.github.neyb.shoulk.matcher.*
import org.junit.jupiter.api.Test

class SwakServer_basicsTest : SwakServerTest() {

    @Test fun `a server can be started and stoped`() {
        swakServer.start()
        swakServer.stop()
    }

    @Test
    internal fun `a server already started cannot be started again`() {
        swakServer.start();
        { swakServer.start() } shouldThrow IllegalStateException::class that hasMessage("server already started!")
    }

    @Test
    internal fun `a not started server cannot be stoped`() {
        { swakServer.stop() } shouldThrow IllegalStateException::class that hasMessage("server not started!")
    }

    @Test
    internal fun `get undefined path return 404`() {
        swakServer.start()
        get(path = "/hello", checkSuccess = false) should match("fail with 404") { it.code() == 404 }
    }
}