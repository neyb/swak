package io.neyb.swak.handler.path

import java.util.regex.PatternSyntaxException

internal object PathPatternCompiler {
    private val groupRegex = """\{(.*?)\}""".toRegex()

    fun compile(pathPattern: String): CompileResult {
        try {
            return tryToCompile(pathPattern)
        } catch (patternException: PatternSyntaxException) {
            throw IllegalPattern(pathPattern, patternException)
        }
    }

    private fun tryToCompile(pathPattern: String): CompileResult {
        checkNoStickenGroups(pathPattern)
        val groupNames = mutableListOf<String>()
        val replace = pathPattern.replace(groupRegex) { matchResult ->
            val groupInfo = matchResult.groups[1]!!
            groupNames += groupInfo.value.substringBefore(':')
            val content = getContentPattern(groupInfo, pathPattern)
            """($content)"""
        }
        return CompileResult(replace.toRegex(), groupNames)
    }

    private fun getContentPattern(groupInfo: MatchGroup, pathPattern: String): String {
        val result = groupInfo.value.substringAfter(':', "")
        return if (!result.isEmpty()) result
        else defaultGroupPattern(groupInfo, pathPattern)
    }

    private fun defaultGroupPattern(group: MatchGroup, pathPattern: String): String {
        val separator = getSeparator(group, pathPattern)
        return if (separator != null) "[^$separator]*" else ".*?"
    }

    private fun getSeparator(groupInfo: MatchGroup, pathPattern: String) =
            if (!groupInfo.value.endsWith(':')) pathPattern.charAfter(groupInfo)
            else null

    private fun String.charAfter(group: MatchGroup) =
            if (this.length > group.range.last + 2) this[group.range.last + 2]
            else null

    private fun checkNoStickenGroups(pattern: String) {
        if (pattern.contains("}{")) throw IllegalPattern(pattern, "sticken groups are not allowed")
    }
}

