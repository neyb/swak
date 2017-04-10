package swak.http

class MutableHeaders(override val allValues: MutableMap<String, List<String>> = mutableMapOf()) : Headers