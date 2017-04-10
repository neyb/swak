package swak

import io.github.neyb.shoulk.*
import org.junit.jupiter.api.Test
import swak.http.request.Method
import swak.http.response.*

class SwakServer_errorHandlerTest : SwakServerTest() {
    open class MyException : RuntimeException()
    class MySubException : MyException()

    @Test fun `can handle a specific error`() {
        swakServer {
            handle<Unit>("/test", Method.GET) {
                throw MyException()
            }
            handle<Unit>("/test2", Method.GET) {
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
                handle<Unit>("/bar", Method.GET) {
                    throw MyException()
                }
                handleError { _: MyException -> NoBodyResponse(status = Code.OK) }
            }
            handleError { _: MyException -> NoBodyResponse(Code.NOT_FOUND) }
        }.start()

        get("/foo/bar").code() shouldEqual 200
    }
}