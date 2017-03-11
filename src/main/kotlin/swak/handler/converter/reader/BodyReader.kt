package swak.handler.converter.reader

interface BodyReader<out B> {
    fun read(body: String): B?
}