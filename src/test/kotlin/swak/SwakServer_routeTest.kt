package swak

import io.github.neyb.shoulk.*
import io.reactivex.Single
import org.junit.jupiter.api.Test
import swak.body.writer.provider.useAlways
import swak.body.writer.BodyWriter
import swak.http.request.Method.GET
import swak.http.request.Method.POST
import swak.http.response.NoBodyResponse
import swak.http.response.SimpleResponse

class SwakServer_routeTest : SwakServerTest() {
    @Test fun `hello world server`() {
        swakServer {
            handle("/hello", GET) {
                Single.just(SimpleResponse(body = "hello world!"))
            }
        }.start()

        get("/hello").body().string() shouldEqual "hello world!"
    }

    @Test fun `several route on same path`() {
        var counter = 0
        swakServer {
            addContentWriterProvider(object : BodyWriter<Int> {
                override fun write(body: Int) = body.toString()
            }.useAlways())
            handle("/count", POST) { counter++;Single.just(NoBodyResponse()) }
            handle("/count", GET) { Single.just(SimpleResponse(body = counter)) }
        }.start()

        post("/count", "")
        post("/count", "")
        get("/count").body().string() shouldEqual "2"
    }

    @Test fun `2 routes with a path containing the other`() {
        swakServer {
            handle("/hello1", GET) { Single.just(NoBodyResponse()) }
            handle("/hello2", GET) { Single.just(NoBodyResponse()) }
        }.start()

        get("/hello1")
        get("/hello2")
    }

    @Test fun `if several route intercept a path, server returns 500`() {
        swakServer {
            handle("/hello", GET) { Single.just(NoBodyResponse()) }
            handle("/hell{thisIsAO}", GET) { Single.just(NoBodyResponse()) }
        }.start()

        val response = get("/hello", checkSuccess = false)
        response.code() shouldEqual 500

    }
}