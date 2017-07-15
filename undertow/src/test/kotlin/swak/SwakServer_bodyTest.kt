package swak

import io.github.neyb.shoulk.shouldEqual
import org.junit.Test
import swak.http.request.Method.POST
import swak.http.response.*

class SwakServer_bodyTest : SwakServerTest() {
    @Test fun `body can be read`() {
        var whoAmI: String? = null
        swakServer {
            on("/IAm", POST) answer {
                whoAmI = request.body()
                SimpleResponse.withoutBody()
            }
        }.start()

        post("/IAm", "Tony Stark")
        whoAmI shouldEqual "Tony Stark"
    }
}