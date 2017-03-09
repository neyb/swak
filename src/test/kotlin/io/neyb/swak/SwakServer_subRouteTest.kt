package io.neyb.swak

import io.neyb.swak.http.Method.GET
import io.neyb.swak.http.Response
import io.reactivex.Single
import org.junit.jupiter.api.Test

class SwakServer_subRouteTest : SwakServerTest(){
    @Test
    internal fun `one route in a sub route`() {
        swakServer {
            sub("/begin") {
                handle("/end", GET) { Single.just(Response()) }
            }
        }.start()

        get("/begin/end")
    }
}