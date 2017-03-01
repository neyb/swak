package io.neyb.swak.http

class Headers(val allValues: Map<String, List<String>>) {
    val singleValues = allValues
            .filterValues { it.size == 1 }
            .mapValues { it.value[0] }
}