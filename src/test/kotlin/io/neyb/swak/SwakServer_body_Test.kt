package io.neyb.swak

import io.github.neyb.shoulk.*
import io.neyb.swak.http.Response
import io.neyb.utils.SwakServerTest
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class SwakServer_body_Test : SwakServerTest() {
    @Test
    internal fun `body can be read`() {
        var whoAmI: String? = null
        swakServer {
            post("/IAm"){ request ->
                request.body
                        .doOnSuccess { whoAmI = it }
                        .map { Response() }
            }
        }.start()

        post("/IAm", "Tony Stark")
        whoAmI shouldEqual "Tony Stark"
    }
}