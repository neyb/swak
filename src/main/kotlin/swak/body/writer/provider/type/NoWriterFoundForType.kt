package swak.body.writer.provider.type

class NoWriterFoundForType(target: Class<*>) : RuntimeException("no writer found for ${target.simpleName}")