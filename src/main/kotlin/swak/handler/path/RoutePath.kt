package swak.handler.path

import swak.http.PathParamExtractor

internal interface RoutePath {
    fun accept(requestPath: String): Boolean
    val extractor: PathParamExtractor

    companion object {
        private val regex: Regex = """\{.*\}""".toRegex()
        fun of(path: String, isFull: Boolean) =
                if (RoutePath.Companion.regex.containsMatchIn(path)) RegexRoutePath(path, isFull)
                else ExactRoutePath(path, isFull)
    }
}