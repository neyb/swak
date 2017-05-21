package swak

import io.github.neyb.shoulk.shouldEqual
import io.reactivex.Single
import org.junit.Test
import swak.http.request.Method.GET
import swak.http.response.*

class SwakServer_subRouteTest : SwakServerTest() {
    @Test
    internal fun `one route in a sub route`() {
        swakServer {
            sub("/begin") {
                on("/end", GET) answer { Single.just(NoBodyResponse()) }
            }
        }.start()

        get("/begin/end")
    }

    @Test
    internal fun `3 recursive routes`() {
        swakServer {
            sub("/begin") {
                sub("/middle") {
                    on("/end", GET) answer { Single.just(NoBodyResponse()) }
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
                    on("/end1", GET) answer { Single.just(SimpleResponse(body = "111")) }
                    on("/end2", GET) answer { Single.just(SimpleResponse(body = "112")) }
                }

                sub("/middle2") {
                    on("/end1", GET) answer { Single.just(SimpleResponse(body = "121")) }
                    on("/end2", GET) answer { Single.just(SimpleResponse(body = "122")) }
                }
            }
            sub("/begin2") {
                sub("/middle1") {
                    on("/end1", GET) answer { Single.just(SimpleResponse(body = "211")) }
                    on("/end2", GET) answer { Single.just(SimpleResponse(body = "212")) }
                }

                sub("/middle2") {
                    on("/end1", GET) answer { Single.just(SimpleResponse(body = "221")) }
                    on("/end2", GET) answer { Single.just(SimpleResponse(body = "222")) }
                }
            }
        }.start()

        get("/begin1/middle1/end1").body()!!.string() shouldEqual "111"
        get("/begin1/middle1/end2").body()!!.string() shouldEqual "112"
        get("/begin1/middle2/end1").body()!!.string() shouldEqual "121"
        get("/begin1/middle2/end2").body()!!.string() shouldEqual "122"
        get("/begin2/middle1/end1").body()!!.string() shouldEqual "211"
        get("/begin2/middle1/end2").body()!!.string() shouldEqual "212"
        get("/begin2/middle2/end1").body()!!.string() shouldEqual "221"
        get("/begin2/middle2/end2").body()!!.string() shouldEqual "222"
    }

    @Test
    internal fun pathParamOfParent() {
        var begin: String? = null
        var middle: String? = null
        var end: String? = null
        swakServer {
            sub("/{begin}") {
                sub("/{middle}") {
                    on("/{end}", GET) answer {
                        begin = request.pathParams["begin"]
                        middle = request.pathParams["middle"]
                        end = request.pathParams["end"]
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