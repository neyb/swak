package io.neyb.swak.handler.path

internal class RegexRoutePath(private val path: String) : RoutePath {
    private val groupNames: List<String>
    private val extractor: Regex

    init {
        val result = PatternCompiler.compile("", path, matchAll = true)
        extractor = result.regex
        groupNames = result.groupNames
    }

    override fun accept(requestPath: String) = extractor.matches(requestPath)

    override fun extractPathParams(requestPath: String) =
            (extractor.matchEntire(requestPath) ?: throw IllegalArgumentException("incompatible path"))
                    .groupValues.withIndex().asSequence()
                    .drop(1)
                    .map { indexedValue -> groupNames[indexedValue.index - 1] to indexedValue.value }
                    .toMap()

    override fun toString() = path
}