package io.neyb.swak

import io.github.neyb.shoulk.*
import io.neyb.swak.http.*
import io.neyb.swak.SwakServerTest
import org.junit.jupiter.api.Test

class SwakServer_body_Test : SwakServerTest() {
    @Test
    internal fun `body can be read`() {
        var whoAmI: String? = null
        swakServer {
            handle(Method.POST, "/IAm") { request :Request->
                request.body
                        .doOnSuccess { whoAmI = it }
                        .map { Response() }
            }
        }.start()

        post("/IAm", "Tony Stark")
        whoAmI shouldEqual "Tony Stark"
    }
}