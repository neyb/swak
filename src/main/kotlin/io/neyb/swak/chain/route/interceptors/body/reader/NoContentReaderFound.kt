package io.neyb.swak.chain.route.interceptors.body.reader

import io.neyb.swak.http.Request

class NoContentReaderFound(request: Request<String>) : Exception("no content reader found for request $request")