package swak

import io.github.neyb.shoulk.shouldEqual
import io.reactivex.Single
import org.junit.Test
import swak.http.request.Method.GET
import swak.http.response.SimpleResponse

class SwakServer_finalRouteExtractTest : SwakServerTest() {

    @Test
    internal fun simpleExtraction() {
        swakServer {
            on("/hello/{who}", GET) answer {
                Single.just(SimpleResponse(body = request.pathParams["who"]))
            }
        }.start()

        get("/hello/boby").body()!!.string() shouldEqual "boby"
    }
}