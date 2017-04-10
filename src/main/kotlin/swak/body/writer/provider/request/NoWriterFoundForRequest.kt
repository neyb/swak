package swak.body.writer.provider.request

import swak.http.request.Request
import swak.http.response.Response

class NoWriterFoundForRequest(request: Request<*>, response: Response<*>) : RuntimeException("no writer found for request/response: $request/$response")