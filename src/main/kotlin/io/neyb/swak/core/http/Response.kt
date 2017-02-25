package io.neyb.swak.core.http

data class Response(
        val status:Status = Status.OK,
        val body: Any? = null
)