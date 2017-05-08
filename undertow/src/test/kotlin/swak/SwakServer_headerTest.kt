package swak

import io.github.neyb.shoulk.*
import io.reactivex.Single
import org.junit.jupiter.api.Test
import swak.http.Headers
import swak.http.request.Method.GET
import swak.http.response.NoBodyResponse

class SwakServer_headerTest : SwakServerTest() {
    @Test
    internal fun `test header`() {
        var header: Headers? = null
        swakServer {
            handle("/", GET) { request ->
                header = request.headers
                Single.just(NoBodyResponse())
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