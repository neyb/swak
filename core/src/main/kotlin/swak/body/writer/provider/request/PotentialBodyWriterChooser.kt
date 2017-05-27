package swak.body.writer.provider.request

import swak.body.writer.BodyWriter
import swak.http.request.Request
import swak.http.response.Response

interface PotentialBodyWriterChooser<in B> {
    fun `for`(response: Response<B>, request: Request<*>): BodyWriter<B>?
}