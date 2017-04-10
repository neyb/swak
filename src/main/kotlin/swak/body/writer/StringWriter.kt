package swak.body.writer

object StringWriter : BodyWriter<String> {
    override fun write(body: String) = body
}