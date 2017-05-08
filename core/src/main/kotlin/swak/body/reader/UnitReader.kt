package swak.body.reader

object UnitReader : BodyReader<Unit> {
    override fun read(body: String) = Unit
}