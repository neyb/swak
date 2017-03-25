package swak

import io.github.neyb.shoulk.*
import org.junit.jupiter.api.Test
import swak.http.request.Method
import swak.http.response.Response

class SwakServer_bodyTest : SwakServerTest() {
    @Test fun `body can be read`() {
        var whoAmI: String? = null
        swakServer {
            handle("/IAm", Method.POST) { request ->
                request.body
                        .doOnSuccess { whoAmI = it }
                        .map { Response() }
            }
        }.start()

        post("/IAm", "Tony Stark")
        whoAmI shouldEqual "Tony Stark"
    }
}