package io.neyb.swak.reader.provider.type

import io.neyb.swak.reader.provider.type.NoReaderFoundForType
import io.neyb.swak.reader.provider.request.BodyReaderRequestProvider
import io.neyb.swak.reader.provider.request.BodyReaderRequestProviders
import java.util.*

class BodyReaderTypeProviders(
        private val parent: BodyReaderTypeProviders? = null
) : BodyReaderTypeProvider {
    private val localTypeProviders: MutableList<BodyReaderTypeProvider> = ArrayList()

    fun add(bodyReaderTypeProvider: BodyReaderTypeProvider) {
        localTypeProviders.add(bodyReaderTypeProvider)
    }

    val allTypeProviders: List<BodyReaderTypeProvider>
        get() = if (parent != null) localTypeProviders + parent.allTypeProviders
        else localTypeProviders

    override fun <B> forClass(target: Class<B>): BodyReaderRequestProvider<B> {
        val requestDependentBodyReaders = allTypeProviders.mapNotNull { it.forClass(target) }
        if (requestDependentBodyReaders.isEmpty()) throw NoReaderFoundForType()
        return BodyReaderRequestProviders(requestDependentBodyReaders)
    }
}