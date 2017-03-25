package swak

import io.github.neyb.shoulk.*
import org.junit.jupiter.api.Test
import swak.http.request.Method
import swak.http.response.Code
import swak.http.response.Response

class SwakServer_errorHandlerTest : SwakServerTest() {
    open class MyException : RuntimeException()
    class MySubException : MyException()

    @Test fun `can handle a specific error`() {
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

    @Test fun `can overwrite a error handler`() {
        swakServer {
            sub("/foo") {
                handle("/bar", Method.GET) {
                    throw MyException()
                }
                handleError<MyException> { Response(status = Code.OK) }
            }
            handleError<MyException> { Response(Code.NOT_FOUND) }
        }.start()

        get("/foo/bar").code() shouldEqual 200
    }
}