package io.swak.handler.path

internal class IllegalPattern
    : RuntimeException {
    constructor(pattern: String, cause: Exception) : super("\"$pattern\" is not a valid pattern ${cause.message}", cause)
    constructor(pattern: String, cause: String) : super("\"$pattern\" is not a valid pattern: $cause")
}