package swak.body.reader

object StringReader : BodyReader<String> {
    override fun read(body: String) = body
}