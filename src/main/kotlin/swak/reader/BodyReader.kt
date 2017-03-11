package swak.reader

interface BodyReader<out B> {
    fun read(body: String): B?
}