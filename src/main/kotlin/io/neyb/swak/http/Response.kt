package io.neyb.swak.http

data class Response(
        val status: Code = Code.OK,
        val body: Any? = null
)