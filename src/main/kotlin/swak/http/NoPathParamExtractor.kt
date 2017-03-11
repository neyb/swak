package swak.http

internal object NoPathParamExtractor : PathParamExtractor {
    override fun extractFrom(requestPath: String) = mapOf<String, String>()
}