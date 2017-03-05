package io.neyb.swak.route.path

interface RoutePath {
    fun accept(requestPath: String): Boolean
    fun extractPathParams(requestPath: String): Map<String, String>

    companion object {
        private val regex: Regex = """\{.*\}""".toRegex()
        fun of(path: String) =
                if (regex.containsMatchIn(path)) RegexRoutePath(path)
                else ExactRoutePath(path)
    }
}