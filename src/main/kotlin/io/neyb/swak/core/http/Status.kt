package io.neyb.swak.core.http

enum class Status(val code: Int) {
    OK(200),
    NOT_FOUND(404),
    INTERNAL_ERROR(500)
}