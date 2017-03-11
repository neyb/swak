package swak.reader.provider.type

import swak.reader.provider.request.BodyReaderRequestProvider
import swak.reader.provider.request.BodyReaderRequestProviders
import java.util.*

internal class BodyReaderTypeProviders(
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