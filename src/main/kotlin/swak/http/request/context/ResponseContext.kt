package swak.http.request.context

import swak.http.response.Response

interface ResponseContext<out ReqBody, out RespBody : Any> : RequestContext<ReqBody> {
    val response: Response<RespBody>
}