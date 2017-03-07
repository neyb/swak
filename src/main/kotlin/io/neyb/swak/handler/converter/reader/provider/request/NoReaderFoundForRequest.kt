package io.neyb.swak.handler.converter.reader.provider.request

import io.neyb.swak.http.Request

class NoReaderFoundForRequest(request: Request<String>) : RuntimeException("no reader found for exception $request")