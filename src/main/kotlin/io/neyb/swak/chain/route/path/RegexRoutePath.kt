package io.neyb.swak.chain.route.path

import java.util.*

class RegexRoutePath(path: String) : RoutePath {
    private val groupNames: MutableList<String> = ArrayList()
    private val extractor: Regex =
            path.replace("""\{(.*?)\}""".toRegex()) { matchResult ->
                val groupName = matchResult.groupValues[1]
                groupNames += groupName
                """(.*?)"""
            }.toRegex()

    override fun accept(requestPath: String) = extractor.matches(requestPath)

    override fun extractPathParams(requestPath: String) =
            (extractor.matchEntire(requestPath) ?: throw IllegalArgumentException("incompatible path"))
                    .groupValues.withIndex().asSequence()
                    .drop(1)
                    .map { indexedValue -> groupNames[indexedValue.index - 1] to indexedValue.value }
                    .toMap()
}