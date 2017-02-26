package io.neyb.swak.http

data class Response @JvmOverloads constructor(
        val status: Status = Status.OK,
        val body: Any? = null
)