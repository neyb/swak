package io.neyb.swak.handler.path

import io.neyb.swak.http.PathParamExtractor

internal interface RoutePath {
    fun accept(requestPath: String): Boolean
    val extractor: PathParamExtractor

    companion object {
        private val regex: Regex = """\{.*\}""".toRegex()
        fun of(path: String) =
                if (regex.containsMatchIn(path)) RegexRoutePath(path)
                else ExactRoutePath(path)
    }
}