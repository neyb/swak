package swak

import io.github.neyb.shoulk.*
import io.reactivex.Single
import org.junit.jupiter.api.Test
import swak.http.request.Method.GET
import swak.http.response.NoBodyResponse
import swak.http.response.SimpleResponse

class SwakServer_subRouteTest : SwakServerTest() {
    @Test
    internal fun `one route in a sub route`() {
        swakServer {
            sub("/begin") {
                handle("/end", GET) { Single.just(NoBodyResponse()) }
            }
        }.start()

        get("/begin/end")
    }

    @Test
    internal fun `3 recursive routes`() {
        swakServer {
            sub("/begin") {
                sub("/middle") {
                    handle("/end", GET) { Single.just(NoBodyResponse()) }
                }
            }
        }.start()

        get("/begin/middle/end")
    }

    @Test
    internal fun `3 levels of 2 routes`() {
        swakServer {
            sub("/begin1") {
                sub("/middle1") {
                    handle("/end1", GET) { Single.just(SimpleResponse(body = "111")) }
                    handle("/end2", GET) { Single.just(SimpleResponse(body = "112")) }
                }

                sub("/middle2") {
                    handle("/end1", GET) { Single.just(SimpleResponse(body = "121")) }
                    handle("/end2", GET) { Single.just(SimpleResponse(body = "122")) }
                }
            }
            sub("/begin2") {
                sub("/middle1") {
                    handle("/end1", GET) { Single.just(SimpleResponse(body = "211")) }
                    handle("/end2", GET) { Single.just(SimpleResponse(body = "212")) }
                }

                sub("/middle2") {
                    handle("/end1", GET) { Single.just(SimpleResponse(body = "221")) }
                    handle("/end2", GET) { Single.just(SimpleResponse(body = "222")) }
                }
            }
        }.start()

        get("/begin1/middle1/end1").body().string() shouldEqual "111"
        get("/begin1/middle1/end2").body().string() shouldEqual "112"
        get("/begin1/middle2/end1").body().string() shouldEqual "121"
        get("/begin1/middle2/end2").body().string() shouldEqual "122"
        get("/begin2/middle1/end1").body().string() shouldEqual "211"
        get("/begin2/middle1/end2").body().string() shouldEqual "212"
        get("/begin2/middle2/end1").body().string() shouldEqual "221"
        get("/begin2/middle2/end2").body().string() shouldEqual "222"
    }

    @Test
    internal fun pathParamOfParent() {
        var begin: String? = null
        var middle: String? = null
        var end: String? = null
        swakServer {
            sub("/{begin}") {
                sub("/{middle}") {
                    handle("/{end}", GET) {
                        begin = it.pathParams["begin"]
                        middle = it.pathParams["middle"]
                        end = it.pathParams["end"]
                        Single.just(NoBodyResponse())
                    }
                }
            }
        }.start()

        get("/foo/bar/yolo")
        begin shouldEqual "foo"
        middle shouldEqual "bar"
        end shouldEqual "yolo"
    }
}