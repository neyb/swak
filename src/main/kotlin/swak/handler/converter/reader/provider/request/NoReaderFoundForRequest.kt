package swak.handler.converter.reader.provider.request

import swak.http.UpdatableRequest

internal class NoReaderFoundForRequest(request: UpdatableRequest<String>)
    : RuntimeException("no reader found for exception $request")