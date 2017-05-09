package swak.http

interface PathParamExtractor{
    fun extractFrom(requestPath: String):Map<String, String>
}