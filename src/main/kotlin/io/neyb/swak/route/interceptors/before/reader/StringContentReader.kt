package io.neyb.swak.route.interceptors.before.reader

import io.neyb.swak.http.Request
import io.neyb.swak.route.interceptors.before.reader.RequestContentReader
import io.neyb.swak.route.interceptors.before.reader.RequestContentReaderProvider
import io.reactivex.Single

object StringContentReader : RequestContentReader<String, String>, RequestContentReaderProvider {
    override fun <BodyBefore, BodyAfter> forClass(fromClass: Class<BodyBefore>, bodyClass: Class<BodyAfter>) =
            if (fromClass == String::class.java && bodyClass == String::class.java)
                @Suppress("UNCHECKED_CAST")
                this as RequestContentReader<BodyBefore, BodyAfter>
            else
                null

    override fun accept(request: Request<String>) =
            request.headers.allValues["content-type"]?.contains("text/plain") ?: true

    override fun updateRequest(request: Request<String>) = Single.just(request)
}