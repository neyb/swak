package swak

import io.github.neyb.shoulk.*
import io.reactivex.Single
import org.junit.jupiter.api.Test
import swak.http.request.Method.GET
import swak.http.response.SimpleResponse

class SwakServer_finalRouteExtractTest : SwakServerTest() {

    @Test
    internal fun simpleExtraction() {
        swakServer {
            handle("/hello/{who}", GET) { request ->
                Single.just(SimpleResponse(body = request.pathParams["who"]))
            }
        }.start()

        get("/hello/boby").body().string() shouldEqual "boby"
    }
}