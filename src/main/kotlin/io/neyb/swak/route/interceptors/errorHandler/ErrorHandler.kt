package io.neyb.swak.route.interceptors.errorHandler

import io.neyb.swak.http.Response

interface ErrorHandler {
    companion object{
        inline fun <reified E:Throwable> of(noinline errorHandler: (E)->Response):ErrorHandler =
                SpecificErrorHandler(E::class.java, errorHandler)
    }

    fun onError(error: Throwable): Response?
}