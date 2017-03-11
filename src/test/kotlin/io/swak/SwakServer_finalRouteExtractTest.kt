package io.swak

import io.github.neyb.shoulk.*
import io.swak.http.Method.GET
import io.swak.http.Response
import io.reactivex.Single
import org.junit.jupiter.api.Test

class SwakServer_finalRouteExtractTest : SwakServerTest() {

    @Test
    internal fun simpleExtraction() {
        swakServer {
            handle("/hello/{who}", GET) { request ->
                Single.just(Response(body = request.pathParams["who"]))
            }
        }.start()

        get("/hello/boby").body().string() shouldEqual "boby"
    }
}