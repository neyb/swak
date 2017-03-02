package io.neyb.swak.chain

import io.neyb.swak.chain.route.interceptors.body.reader.RequestContentReader

interface RequestContentReaderProvider {
    fun <B> forClass(bodyClass: Class<B>): RequestContentReader<B>?
}