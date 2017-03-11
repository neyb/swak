package swak

import io.github.neyb.shoulk.*
import org.junit.jupiter.api.Test
import swak.http.Method
import swak.http.Response

class SwakServer_bodyTest : SwakServerTest() {
    @Test
    internal fun `body can be read`() {
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