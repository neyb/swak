package io.swak.http

import io.reactivex.Single

interface Request<B> {
    val headers: Headers
    val path: String
    val pathParams: Map<String, String>
    val method: Method
    val body: Single<B?>
    val additionalData: AdditionalData
}