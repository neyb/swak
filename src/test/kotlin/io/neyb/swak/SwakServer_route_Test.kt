package io.neyb.swak

import io.github.neyb.shoulk.*
import io.neyb.swak.http.Response
import io.neyb.utils.SwakServerTest
import io.reactivex.Single
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class SwakServer_route_Test : SwakServerTest() {
    @Test fun `hello world server`() {
        swakServer {
            get("/hello") {
                Single.just(
                        io.neyb.swak.http.Response(body = "hello world!"))
            }
        }.start()

        get("/hello").body().string() shouldEqual "hello world!"
    }

    @Test fun `several route on same path`() {
        var counter = 0
        swakServer {
            post("/count") { counter++;Single.just(Response()) }
            get("/count") { Single.just(Response(body = counter)) }
        }.start()

        post("/count", "")
        post("/count", "")
        get("/count").body().string() shouldEqual "2"
    }
}