package io.neyb.swak.handler.path

import io.neyb.swak.http.BasicPathParamExtractor
import io.neyb.swak.http.PathParamExtractor

internal class RegexRoutePath(private val path: String) : RoutePath {
    private val matcher: Regex
    override val extractor: PathParamExtractor

    init {
        val result = PathPatternCompiler.compile(path)
        matcher = result.regex
        extractor = BasicPathParamExtractor(result.regex, result.groupNames)
    }

    override fun accept(requestPath: String) = matcher.matches(requestPath)

    override fun toString() = path
}