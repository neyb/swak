package io.neyb.swak.route.interceptors.before.reader

interface RequestContentReaderProvider {
    fun <BodyBefore, BodyAfter> forClass(
            fromClass: Class<BodyBefore>,
            bodyClass: Class<BodyAfter>
    ): RequestContentReader<BodyBefore, BodyAfter>?
}