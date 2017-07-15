package swak

import io.github.neyb.shoulk.shouldEqual
import org.junit.Test
import swak.body.writer.BodyWriter
import swak.body.writer.provider.useAlways
import swak.http.request.Method.*
import swak.http.response.*

class SwakServer_routeTest : SwakServerTest() {
    @Test fun `hello world server`() {
        swakServer {
            on("/hello", GET) answer {
                SimpleResponse(body = "hello world!")
            }
        }.start()

        get("/hello").body()!!.string() shouldEqual "hello world!"
    }

    @Test fun `several route on same path`() {
        var counter = 0
        swakServer {
            addContentWriterProvider(object : BodyWriter<Int> {
                override fun write(body: Int) = body.toString()
            }.useAlways())
            on("/count", POST) answer { counter++;SimpleResponse.withoutBody() }
            on("/count", GET) answer { SimpleResponse(body = counter) }
        }.start()

        post("/count", "")
        post("/count", "")
        get("/count").body()!!.string() shouldEqual "2"
    }

    @Test fun `2 routes with a path containing the other`() {
        swakServer {
            on("/hello1", GET) answer { SimpleResponse.withoutBody() }
            on("/hello2", GET) answer { SimpleResponse.withoutBody() }
        }.start()

        get("/hello1")
        get("/hello2")
    }

    @Test fun `if several route intercept a path, server returns 500`() {
        swakServer {
            on("/hello", GET) answer { SimpleResponse.withoutBody() }
            on("/hell{thisIsAO}", GET) answer { SimpleResponse.withoutBody() }
        }.start()

        val response = get("/hello", checkSuccess = false)
        response.code() shouldEqual 500

    }
}