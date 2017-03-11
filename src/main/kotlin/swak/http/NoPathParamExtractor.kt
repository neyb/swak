package swak.http

class NoPathParamExtractor : PathParamExtractor {
    override fun extractFrom(requestPath: String) = mapOf<String, String>()
}