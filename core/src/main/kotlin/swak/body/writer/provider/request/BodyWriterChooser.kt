package swak.body.writer.provider.request

import swak.body.writer.BodyWriter
import swak.http.request.Request
import swak.http.response.Response

interface BodyWriterChooser<in B> : PotentialBodyWriterChooser<B> {
    override fun `for`(response: Response<B>, request: Request<*>): BodyWriter<B>
}