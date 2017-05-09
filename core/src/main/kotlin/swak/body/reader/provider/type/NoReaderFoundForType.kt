package swak.body.reader.provider.type

internal class NoReaderFoundForType(target: Class<*>) : RuntimeException("no reader for type ${target.simpleName}")