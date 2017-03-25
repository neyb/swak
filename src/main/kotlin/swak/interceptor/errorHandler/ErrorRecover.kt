package swak.interceptor.errorHandler

import swak.http.requestContext.UpdatableResponseContext

///TODO generify on subject, not on body
internal sealed class ErrorRecover<out BodyIn> {
    abstract val responseContext: UpdatableResponseContext<BodyIn>

    class SafeErrorRecover<out BodyIn>(override val responseContext: UpdatableResponseContext<BodyIn>) : ErrorRecover<BodyIn>()

    class RethrowErrorRecover<out BodyIn>(
            private val baseError: Throwable,
            private val possibleResponseContext: UpdatableResponseContext<BodyIn>?
    ) : ErrorRecover<BodyIn>() {
        override val responseContext: UpdatableResponseContext<BodyIn>
            get() = possibleResponseContext ?: throw baseError
    }
}