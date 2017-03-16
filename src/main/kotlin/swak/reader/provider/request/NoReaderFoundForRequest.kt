package swak.reader.provider.request

import swak.http.request.UpdatableRequest

internal class NoReaderFoundForRequest(request: UpdatableRequest<String>)
    : RuntimeException("no reader found for exception $request")