package io.neyb.swak.handler.converter.reader.provider.request

import io.neyb.swak.http.UpdatableRequest

class NoReaderFoundForRequest(request: UpdatableRequest<String>) : RuntimeException("no reader found for exception $request")