package io.neyb.swak.handler.path

internal object PatternCompiler {
    private val groupRegex = """\{(.*?)\}""".toRegex()

    fun compile(prePath: String, path: String, matchAll: Boolean): CompileResult {
        checkNoStickenGroups(prePath + path)
        val (prePathPattern, _) = regexForPath(prePath, capture = false)
        val (pathPattern, groupNames) = regexForPath(path, capture = true)

        return CompileResult(regexFor(prePathPattern, pathPattern, matchAll), groupNames)
    }

    private fun checkNoStickenGroups(globalInput: String) {
        if (globalInput.contains("}{")) throw IllegalArgumentException("sticken groups are not allowed")
    }

    private fun regexForPath(path: String, capture: Boolean): Pair<String, List<String>> {
        val groupNames = mutableListOf<String>()
        val replace = path.replace(groupRegex) { matchResult ->
            val groupInfo = matchResult.groupValues[1]
            groupNames += groupInfo.substringBeforeLast(':')

            val separators: String = groupInfo.substringAfterLast(':', "")
                    .ifEmpty { getSeparator(matchResult, path) }
            val replacement = """[^$separators]*?"""
            if (capture) "($replacement)"
            else replacement
        }
        return replace to groupNames
    }

    private fun String.ifEmpty(supplier: () -> String) =
            if (this.isEmpty()) supplier()
            else this

    private fun getSeparator(matchResult: MatchResult, path: String): String {
        val matchGroup = matchResult.groups[0]!!
        val nextCharIndex = matchGroup.range.endInclusive + 1
        val separator: String =
                if (path.length > nextCharIndex) path[nextCharIndex].toString()
                else "/"
        return separator
    }


    private fun regexFor(prePathRegex: String, pathRegex: String, matchAll: Boolean) =
            patternFor(prePathRegex, pathRegex, matchAll).toRegex()

    private fun patternFor(prePathRegex: String, pathRegex: String, matchAll: Boolean) =
            if (matchAll) "^$prePathRegex$pathRegex$"
            else prePathRegex + pathRegex
}