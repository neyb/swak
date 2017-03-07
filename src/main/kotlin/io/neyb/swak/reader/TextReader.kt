package io.neyb.swak.reader

object TextReader : BodyReader<String>{
    override fun read(body: String) = body
}