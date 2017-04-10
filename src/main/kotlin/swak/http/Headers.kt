package swak.http

interface Headers {
    val allValues: Map<String, List<String>>
    val singleValues: Map<String, String>
        get() = allValues
                .filterValues { it.size == 1 }
                .mapValues { it.value[0] }
}