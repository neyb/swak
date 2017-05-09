package swak.body.reader.provider.type

import swak.body.reader.provider.request.BodyReaderChooser
import swak.body.reader.provider.request.BodyReaderRequestProviders
import java.util.*

internal class BodyReaderChooserProviders(
        private val parent: BodyReaderChooserProviders? = null
) : BodyReaderChooserProvider {
    private val localChooserProviders: MutableList<BodyReaderChooserProvider> = ArrayList()

    fun add(bodyReaderChooserProvider: BodyReaderChooserProvider) {
        localChooserProviders.add(bodyReaderChooserProvider)
    }

    val allChooserProviders: List<BodyReaderChooserProvider>
        get() = if (parent != null) localChooserProviders + parent.allChooserProviders
        else localChooserProviders

    override fun <B> forClass(target: Class<B>): BodyReaderChooser<B> {
        val requestDependentBodyReaders = allChooserProviders.mapNotNull { it.forClass(target) }
        if (requestDependentBodyReaders.isEmpty()) throw NoReaderFoundForType(target)
        return BodyReaderRequestProviders(requestDependentBodyReaders)
    }
}