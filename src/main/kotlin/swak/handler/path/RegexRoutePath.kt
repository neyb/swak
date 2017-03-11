package swak.handler.path

import swak.http.PathParamExtractor
import swak.http.RegexPathParamExtractor

internal class RegexRoutePath(
        private val path: String,
        private val full: Boolean) : RoutePath {
    private val matcher: Regex
    override val extractor: PathParamExtractor

    init {
        val result = PathPatternCompiler.compile(path)
        matcher = result.regex
        extractor = RegexPathParamExtractor(result.regex, result.groupNames)
    }

    override fun accept(requestPath: String) =
            if(full)matcher.matches(requestPath)
            else matcher.containsMatchIn(requestPath)

    override fun toString() = path
}