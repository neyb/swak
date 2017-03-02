package io.neyb.swak.chain.route.interceptors.body.reader

import io.neyb.swak.http.Request

class SeveralContentReaderFound(request: Request<String>) :
        Exception("several content reader found for request $request")