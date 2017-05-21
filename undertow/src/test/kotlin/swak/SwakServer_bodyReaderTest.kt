package swak

import io.github.neyb.shoulk.shouldEqual
import org.junit.Test
import swak.body.reader.BodyReader
import swak.body.reader.provider.request.BodyReaderChooser
import swak.body.reader.provider.type.BodyReaderChooserProvider
import swak.http.request.Method.POST
import swak.http.request.UpdatableRequest
import swak.http.response.NoBodyResponse

class SwakServer_bodyReaderTest : SwakServerTest() {

    @Test fun body_can_be_read() {
        val bodyLengthReader = object : BodyReader<Int>, BodyReaderChooserProvider, BodyReaderChooser<Int> {
            override fun read(body: String) = body.length

            override fun <B> forClass(target: Class<B>) =
                    @Suppress("UNCHECKED_CAST")
                    if (target == Int::class.javaObjectType) this as BodyReaderChooser<B>
                    else null

            override fun forRequest(request: UpdatableRequest<String>) = this
        }

        var nameLength: Int? = null
        swakServer {
            addContentReaderProvider(bodyLengthReader)

            on("/IAm", POST).withA<Int>() answer {
                request.body
                        .doOnSuccess { nameLength = it }
                        .map { NoBodyResponse() }
            }

        }.start()

        post("/IAm", "Tony Stark")
        nameLength shouldEqual 10
    }
}