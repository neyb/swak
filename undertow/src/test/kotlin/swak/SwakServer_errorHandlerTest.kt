package swak

import io.github.neyb.shoulk.shouldEqual
import org.junit.Test
import swak.http.request.Method.GET
import swak.http.response.*

class SwakServer_errorHandlerTest : SwakServerTest() {
    open class MyException : RuntimeException()
    class MySubException : MyException()

    @Test fun `can handle a specific error`() {
        swakServer {
            on("/test", GET).answer<Unit> {
                throw MyException()
            }
            on("/test2", GET).answer<Unit> {
                throw MySubException()
            }
            handleError { _: MyException -> NoBodyResponse(Code.PARTIAL_CONTENT) }
        }.start()

        get("/test").code() shouldEqual 206
        get("/test2").code() shouldEqual 206
    }

    @Test fun `can overwrite a error handler`() {
        swakServer {
            sub("/foo") {
                on("/bar", GET).answer<Unit> {
                    throw MyException()
                }
                handleError { _: MyException -> NoBodyResponse(status = Code.OK) }
            }
            handleError { _: MyException -> NoBodyResponse(Code.NOT_FOUND) }
        }.start()

        get("/foo/bar").code() shouldEqual 200
    }
}