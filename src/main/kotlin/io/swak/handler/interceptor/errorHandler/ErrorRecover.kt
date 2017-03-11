package io.swak.handler.interceptor.errorHandler

import io.swak.http.Response

internal sealed class ErrorRecover {
    abstract val response: Response

    class SafeErrorRecover(override val response: Response) : ErrorRecover()

    class RethrowErrorRecover(
            private val baseError: Throwable,
            private val possibleResponse: Response?
    ) : ErrorRecover() {
        override val response: Response
            get() = possibleResponse ?: throw baseError
    }
}