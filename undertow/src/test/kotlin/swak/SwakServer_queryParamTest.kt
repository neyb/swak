package swak

import io.github.neyb.shoulk.shouldEqual
import io.reactivex.Single
import org.junit.Test
import swak.http.request.Method
import swak.http.response.*

class SwakServer_queryParamTest :SwakServerTest() {
    @Test
    fun `can get a query param`() {
        var params :Map<String, List<String>>? = null
        swakServer {
            on("/test", Method.GET) answer  {
                params = request.queryParams
                Single.just(SimpleResponse.withoutBody())
            }
        }.start()

        get("/test?param1=1&param2=2&param2=3")

        params shouldEqual mapOf("param1" to listOf("1"), "param2" to listOf("2","3"))
    }
}