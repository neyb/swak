package swak

import io.github.neyb.shoulk.*
import org.junit.jupiter.api.Test
import swak.http.*
import swak.reader.BodyReader
import swak.reader.provider.request.BodyReaderRequestProvider
import swak.reader.provider.type.BodyReaderTypeProvider

class SwakServer_bodyReaderTest : SwakServerTest() {

    @Test
    internal fun body_can_be_read() {
        val bodyLengthReader = object : BodyReader<Int>, BodyReaderTypeProvider, BodyReaderRequestProvider<Int> {
            override fun read(body: String) = body.length

            override fun <B> forClass(target: Class<B>) =
                    @Suppress("UNCHECKED_CAST")
                    if (target == Int::class.javaObjectType) this as BodyReaderRequestProvider<B>
                    else null

            override fun forRequest(request: UpdatableRequest<String>) = this
        }

        var nameLength: Int? = null
        swakServer {
            addContentReaderProvider(bodyLengthReader)

            handleTyped<Int>(Method.POST, "/IAm") { request ->
                request.body
                        .doOnSuccess { nameLength = it }
                        .map { Response() }
            }
        }.start()

        post("/IAm", "Tony Stark")
        nameLength shouldEqual 10
    }
}