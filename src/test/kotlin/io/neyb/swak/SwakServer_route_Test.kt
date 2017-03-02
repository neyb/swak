package io.neyb.swak

import io.github.neyb.shoulk.*
import io.neyb.swak.http.Method.GET
import io.neyb.swak.http.Method.POST
import io.neyb.swak.http.Response
import io.reactivex.Single
import org.junit.jupiter.api.Test

class SwakServer_route_Test : SwakServerTest() {
    @Test fun `hello world server`() {
        swakServer {
            handle(GET, "/hello") {
                Single.just(Response(body = "hello world!"))
            }
        }.start()

        get("/hello").body().string() shouldEqual "hello world!"
    }

    @Test fun `several route on same path`() {
        var counter = 0
        swakServer {
            handle(POST, "/count") { counter++;Single.just(Response()) }
            handle(GET, "/count") { Single.just(Response(body = counter)) }
        }.start()

        post("/count", "")
        post("/count", "")
        get("/count").body().string() shouldEqual "2"
    }

    @Test fun `if several route intercept a path, server returns 500`() {
        swakServer {
            handle(GET, "/hello") { Single.just(Response()) }
            handle(GET, "/hell{thisIsAO}") { Single.just(Response()) }
        }.start()

        val response = get("/hello", checkSuccess = false)
        response.code() shouldEqual 500

    }
}