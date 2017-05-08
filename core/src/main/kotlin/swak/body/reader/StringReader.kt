package swak.body.reader

internal object StringReader : BodyReader<String> {
    override fun read(body: String) = body
}