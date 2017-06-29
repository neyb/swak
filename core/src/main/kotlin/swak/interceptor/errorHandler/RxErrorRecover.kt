package swak.interceptor.errorHandler

import swak.http.response.context.UpdatableResponseContext

internal sealed class RxErrorRecover<out reqBody, out OB> {
    abstract val responseContext: UpdatableResponseContext<reqBody, OB>

    class SafeRxErrorRecover<out BodyIn, out OB>(
            override val responseContext: UpdatableResponseContext<BodyIn, OB>
    ) : RxErrorRecover<BodyIn, OB>()

    class RethrowRxErrorRecover<out reqBody, out OB>(
            private val baseError: Throwable,
            private val possibleResponseContext: UpdatableResponseContext<reqBody, OB>?
    ) : RxErrorRecover<reqBody, OB>() {
        override val responseContext: UpdatableResponseContext<reqBody, OB>
            get() = possibleResponseContext ?: throw baseError
    }
}