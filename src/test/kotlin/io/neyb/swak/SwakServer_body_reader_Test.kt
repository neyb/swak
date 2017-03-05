package io.neyb.swak

import io.github.neyb.shoulk.*
import io.neyb.swak.http.*
import io.neyb.swak.route.interceptors.before.reader.RequestContentReader
import io.neyb.swak.route.interceptors.before.reader.RequestContentReaderProvider
import io.reactivex.Single
import org.junit.jupiter.api.Test

class SwakServer_body_Reader_Test : SwakServerTest() {


    @Test
    internal fun `body can be read`() {
        @Suppress("UNCHECKED_CAST")
        val bodyLengthReader = object : RequestContentReader<String, Int>, RequestContentReaderProvider {
            override fun <BodyBefore, BodyAfter> forClass(fromClass: Class<BodyBefore>, bodyClass: Class<BodyAfter>) =
                    if (bodyClass == Int::class.javaObjectType) this as RequestContentReader<BodyBefore, BodyAfter>
                    else null

            override fun accept(request: Request<String>) = true

            override fun updateRequest(request: Request<String>) =
                    Single.just(request.withBodyReader { it.length })
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