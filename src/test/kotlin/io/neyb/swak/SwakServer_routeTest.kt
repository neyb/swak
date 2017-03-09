package io.neyb.swak

import io.github.neyb.shoulk.*
import io.neyb.swak.http.Method.GET
import io.neyb.swak.http.Method.POST
import io.neyb.swak.http.Response
import io.reactivex.Single
import org.junit.jupiter.api.Test

class SwakServer_routeTest : SwakServerTest() {
    @Test fun `hello world server`() {
        swakServer {
            handle("/hello", GET) {
                Single.just(Response(body = "hello world!"))
            }
        }.start()

        get("/hello").body().string() shouldEqual "hello world!"
    }

    @Test fun `several route on same path`() {
        var counter = 0
        swakServer {
            handle("/count", POST) { counter++;Single.just(Response()) }
            handle("/count", GET) { Single.just(Response(body = counter)) }
        }.start()

        post("/count", "")
        post("/count", "")
        get("/count").body().string() shouldEqual "2"
    }

    @Test fun `2 routes with a path containing the other`() {
        swakServer {
            handle("/hello1", GET) { Single.just(Response()) }
            handle("/hello2", GET) { Single.just(Response()) }
        }.start()

        get("/hello1")
        get("/hello2")
    }

    @Test fun `if several route intercept a path, server returns 500`() {
        swakServer {
            handle("/hello", GET) { Single.just(Response()) }
            handle("/hell{thisIsAO}", GET) { Single.just(Response()) }
        }.start()

        val response = get("/hello", checkSuccess = false)
        response.code() shouldEqual 500

    }
}