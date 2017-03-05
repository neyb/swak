package io.neyb.swak.route.interceptors.before.reader

import io.neyb.swak.http.Request

class SeveralContentReaderFound(request: Request<*>) :
        Exception("several content reader found for request $request")