package io.neyb.swak

import io.github.neyb.shoulk.*
import io.neyb.swak.http.Method.GET
import io.neyb.swak.http.Response
import io.reactivex.Single
import org.junit.jupiter.api.Test

class SwakServer_finalRouteExtractTest : SwakServerTest() {

    @Test
    internal fun simpleExtraction() {
        swakServer {
            handle(GET, "/hello/{who}") { request ->
                Single.just(Response(body = request.pathParams["who"]))
            }
        }.start()

        get("/hello/boby").body().string() shouldEqual "boby"
    }
}