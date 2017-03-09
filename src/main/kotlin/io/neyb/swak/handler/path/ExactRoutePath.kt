package io.neyb.swak.handler.path

import io.neyb.swak.http.NoPathParamExtractor

class ExactRoutePath(private val path: String) : RoutePath {
    override fun accept(requestPath: String) = requestPath == path
    override val extractor = NoPathParamExtractor()
    override fun toString() = path
}