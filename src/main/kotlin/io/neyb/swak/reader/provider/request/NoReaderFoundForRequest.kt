package io.neyb.swak.reader.provider.request

import io.neyb.swak.http.Request

class NoReaderFoundForRequest(request: Request<*>) : RuntimeException()