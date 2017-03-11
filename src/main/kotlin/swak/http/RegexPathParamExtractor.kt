package swak.http

internal class RegexPathParamExtractor(
        private val extractor: Regex,
        private val groupNames: List<String>) : PathParamExtractor {
    override fun extractFrom(requestPath: String) =
            (extractor.matchEntire(requestPath) ?: throw IllegalArgumentException("incompatible path"))
                    .groupValues.withIndex().asSequence()
                    .drop(1)
                    .map { indexedValue -> groupNames[indexedValue.index - 1] to indexedValue.value }
                    .toMap()
}