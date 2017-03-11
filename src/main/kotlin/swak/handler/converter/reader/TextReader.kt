package swak.handler.converter.reader

internal object TextReader : BodyReader<String> {
    override fun read(body: String) = body
}