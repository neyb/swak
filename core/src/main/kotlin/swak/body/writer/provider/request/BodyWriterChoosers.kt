package swak.body.writer.provider.request

import swak.body.writer.BodyWriter
import swak.http.request.Request
import swak.http.response.Response

class BodyWriterChoosers<in B>(
        private val writerChoosers: List<PotentialBodyWriterChooser<B>>
) : BodyWriterChooser<B> {
    override fun `for`(response: Response<*>, request: Request<*>): BodyWriter<B> {
        return writerChoosers
                .mapNotNull { it.`for`(response, request) }
                .firstOrNull()
                ?: throw NoWriterFoundForRequest(request, response)
    }
}