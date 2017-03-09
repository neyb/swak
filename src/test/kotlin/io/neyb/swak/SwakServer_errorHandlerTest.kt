package io.neyb.swak

import io.github.neyb.shoulk.*
import io.neyb.swak.http.*
import org.junit.jupiter.api.Test

class SwakServer_errorHandlerTest : SwakServerTest() {
    open class MyException : RuntimeException()
    class MySubException : MyException()

    @Test
    internal fun `can handle a specific error`() {

        swakServer {
            handle("/test", Method.GET) {
                throw MyException()
            }
            handle("/test2", Method.GET) {
                throw MySubException()
            }
            handleError<MyException> { Response(Code.PARTIAL_CONTENT) }
        }.start()

        get("/test").code() shouldEqual 206
        get("/test2").code() shouldEqual 206
    }
}