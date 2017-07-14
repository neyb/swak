package swak.body.reader

interface BodyReader<out B> {
    fun read(body: String): B
}