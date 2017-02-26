package io.neyb.swak

import io.github.neyb.shoulk.*
import io.neyb.swak.http.Response
import io.neyb.utils.SwakServerTest
import io.reactivex.Single
import org.junit.jupiter.api.Test

class SwakServer_route_extract_Test : SwakServerTest(){

    @Test
    internal fun simpleExtraction() {
        swakServer {
            get("/hello/{who}") { request ->
                Single.just(Response(body = request.pathParams["who"]))
            }
        }.start()

        get("/hello/boby").body().string() shouldEqual "boby"
    }
}