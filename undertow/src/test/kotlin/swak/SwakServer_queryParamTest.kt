package swak

import io.github.neyb.shoulk.*
import io.reactivex.Single
import org.junit.jupiter.api.Test
import swak.http.request.Method
import swak.http.response.NoBodyResponse

class SwakServer_queryParamTest :SwakServerTest() {
    @Test
    fun `can get a query param`() {
        var params :Map<String, List<String>>? = null
        swakServer {
            handle("/test", Method.GET) { request ->
                params = request.queryParams
                Single.just(NoBodyResponse())
            }
        }.start()

        get("/test?param1=1&param2=2&param2=3")

        params shouldEqual mapOf("param1" to listOf("1"), "param2" to listOf("2","3"))
    }
}