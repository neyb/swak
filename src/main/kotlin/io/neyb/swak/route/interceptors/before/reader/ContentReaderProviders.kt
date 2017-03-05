package io.neyb.swak.route.interceptors.before.reader

import java.util.*

class ContentReaderProviders(
        private val parentProviders: ContentReaderProviders? = null
) : RequestContentReaderProvider {
    private val contentReaderProviders: MutableList<RequestContentReaderProvider> = ArrayList()
    fun add(requestContentReaderProvider: RequestContentReaderProvider) {
        contentReaderProviders.add(requestContentReaderProvider)
    }

    override fun <BodyBefore, BodyAfter> forClass(fromClass: Class<BodyBefore>, bodyClass: Class<BodyAfter>): RequestContentReader<BodyBefore, BodyAfter>? {
        val requestContentReaders = contentReaderProviders
                .mapNotNull { it.forClass(fromClass, bodyClass) }
        return if (!requestContentReaders.isEmpty()) RequestContentReaders(requestContentReaders)
        else parentProviders?.forClass(fromClass, bodyClass)
    }
}