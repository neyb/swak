package io.neyb.swak.handler.converter.reader

object TextReader : BodyReader<String>{
    override fun read(body: String) = body
}