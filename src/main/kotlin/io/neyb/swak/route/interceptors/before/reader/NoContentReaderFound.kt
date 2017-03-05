package io.neyb.swak.route.interceptors.before.reader

import io.neyb.swak.http.Request

class NoContentReaderFound(request: Request<*>) : Exception("no content reader found for request $request")