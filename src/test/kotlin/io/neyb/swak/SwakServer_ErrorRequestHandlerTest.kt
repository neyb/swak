package io.neyb.swak

import io.github.neyb.shoulk.*
import io.neyb.swak.http.*
import org.junit.jupiter.api.Test

class SwakServer_ErrorHandlerTest : SwakServerTest() {
    open class MyException : RuntimeException()
    class MySubException : MyException()

    @Test
    internal fun `can handle a specific error`() {
        swakServer {
            handle(Method.GET, "/test") {
                throw MyException()
            }
            handle(Method.GET, "/test2") {
                throw MySubException()
            }
            handleError<MyException> { Response(Code.PARTIAL_CONTENT) }
        }.start()

        get("/test").code() shouldEqual 206
        get("/test2").code() shouldEqual 206
    }
}