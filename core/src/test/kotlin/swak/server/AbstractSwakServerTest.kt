package swak.server

import io.github.neyb.shoulk.hasMessage
import io.github.neyb.shoulk.shouldBe
import io.github.neyb.shoulk.shouldThrow
import io.github.neyb.shoulk.that
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import swak.handler.Handler

@Suppress("UNCHECKED_CAST")
class AbstractSwakServerTest {

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T


    inner class TestSwakServer : AbstractSwakServer({}, rootHandlerInitializer) {
        var started = false

        override fun doStart(rootHandler: Handler<String>) {
            started = true
        }

        override fun doStop() {
            started = false
        }
    }

    val rootHandlerInitializer: RootHandlerInitialiazer = mock(RootHandlerInitialiazer::class.java)
    val rootHandler: Handler<String> = mock(Handler::class.java) as Handler<String>
    val ss: TestSwakServer by lazy { TestSwakServer() }

    @BeforeEach fun setUp() {
        `when`(rootHandlerInitializer.initialise(any())).thenReturn(rootHandler)
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

    @Test fun `cannot stop a stoped server`() {
        {ss.stop()} shouldThrow IllegalStateException::class that hasMessage("server not started!")
    }
}