package io.swak

import io.github.neyb.shoulk.*
import io.swak.http.Headers
import io.swak.http.Method.GET
import io.swak.http.Response
import io.reactivex.Single
import org.junit.jupiter.api.Test

class SwakServer_headerTest : SwakServerTest() {
    @Test
    internal fun `test header`() {
        var header: Headers? = null
        swakServer {
            handle("/", GET) { request ->
                header = request.headers
                io.reactivex.Single.just(Response())
            }
        }.start()

        send("/") {
            it.get()
                    .addHeader("key", "value1")
                    .addHeader("key", "value2")
                    .addHeader("another_key", "another value")
        }

        header!!.allValues["key"] shoudHaveValueThat equal(listOf("value1", "value2"))
        header!!.singleValues["key"] shouldBe null

        header!!.allValues["another_key"] shoudHaveValueThat equal(listOf("another value"))
        header!!.singleValues["another_key"] shouldEqual "another value"
    }
}