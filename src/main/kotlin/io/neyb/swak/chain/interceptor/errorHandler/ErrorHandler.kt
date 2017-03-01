package io.neyb.swak.chain.interceptor.errorHandler

import io.neyb.swak.http.Response

interface ErrorHandler {
    fun onError(error: Throwable): Response?
}