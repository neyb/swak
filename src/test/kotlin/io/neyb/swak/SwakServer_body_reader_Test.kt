package io.neyb.swak

import io.github.neyb.shoulk.*
import io.neyb.swak.http.*
import io.neyb.swak.handler.converter.reader.BodyReader
import io.neyb.swak.handler.converter.reader.provider.request.BodyReaderRequestProvider
import io.neyb.swak.handler.converter.reader.provider.type.BodyReaderTypeProvider
import org.junit.jupiter.api.Test

class SwakServer_body_Reader_Test : SwakServerTest() {


    @Test
    internal fun `body can be read`() {
        @Suppress("UNCHECKED_CAST")
        val bodyLengthReader = object : BodyReader<Int>, BodyReaderTypeProvider, BodyReaderRequestProvider<Int> {
            override fun read(body: String) = body.length

            override fun <B> forClass(target: Class<B>) =
                    if (target == Int::class.javaObjectType) this as BodyReaderRequestProvider<B>
                    else null

            override fun forRequest(request: Request<String>): BodyReader<Int>//compiler bug : need too specify return type
                    = this
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