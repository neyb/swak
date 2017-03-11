package swak.reader

internal object TextReader : BodyReader<String> {
    override fun read(body: String) = body
}