package swak.http

import java.util.*

class AdditionalData {
    private val content: MutableMap<String, Any> = HashMap()

    operator fun set(key: String, value: Any) {
        content[key] = value
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <E> get(key: String) = content[key] as E
}