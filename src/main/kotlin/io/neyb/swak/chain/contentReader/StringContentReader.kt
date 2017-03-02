package io.neyb.swak.chain.contentReader

import io.neyb.swak.chain.RequestContentReaderProvider
import io.neyb.swak.chain.route.interceptors.body.reader.RequestContentReader
import io.neyb.swak.http.Request
import io.reactivex.Single

object StringContentReader : RequestContentReader<String>, RequestContentReaderProvider {
    override fun <B> forClass(bodyClass: Class<B>): RequestContentReader<B>? =
            if (bodyClass == String::class.java)
                @Suppress("UNCHECKED_CAST")
                this as RequestContentReader<B>
            else
                null

    override fun accept(request: Request<String>) =
            request.headers.allValues["content-type"]?.contains("text/plain") ?: true

    override fun updateRequest(request: Request<String>) = Single.just(request)
}