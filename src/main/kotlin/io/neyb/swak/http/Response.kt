package io.neyb.swak.http

data class Response @JvmOverloads constructor(
        val status: Code = Code.OK,
        val body: Any? = null
)