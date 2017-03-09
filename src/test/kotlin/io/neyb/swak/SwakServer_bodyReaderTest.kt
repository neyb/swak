package io.neyb.swak

import io.github.neyb.shoulk.*
import io.neyb.swak.handler.converter.reader.BodyReader
import io.neyb.swak.handler.converter.reader.provider.request.BodyReaderRequestProvider
import io.neyb.swak.handler.converter.reader.provider.type.BodyReaderTypeProvider
import io.neyb.swak.http.*
import org.junit.jupiter.api.Test

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