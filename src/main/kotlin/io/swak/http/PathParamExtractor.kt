package io.swak.http

internal interface PathParamExtractor{
    fun extractFrom(requestPath: String):Map<String, String>
}