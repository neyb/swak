package swak.interceptor.errorHandler

import swak.http.response.context.UpdatableResponseContext

internal sealed class RxErrorRecover<out reqBody> {
    abstract val responseContext: UpdatableResponseContext<reqBody>

    class SafeRxErrorRecover<out BodyIn>(
            override val responseContext: UpdatableResponseContext<BodyIn>
    ) : RxErrorRecover<BodyIn>()

    class RethrowRxErrorRecover<out reqBody>(
            private val baseError: Throwable,
            private val possibleResponseContext: UpdatableResponseContext<reqBody>?
    ) : RxErrorRecover<reqBody>() {
        override val responseContext: UpdatableResponseContext<reqBody>
            get() = possibleResponseContext ?: throw baseError
    }
}