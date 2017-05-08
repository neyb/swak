package swak.body.writer

interface BodyWriter<in B> {
    fun write(body: B): String
}