package swak.body.writer.provider.type

import swak.body.writer.provider.request.BodyWriterChooser
import swak.body.writer.provider.request.BodyWriterChoosers
import java.util.*

class BodyWriterChooserProviders(
        private val parent: BodyWriterChooserProviders?
) : BodyWriterChooserProvider {
    private val localChooserProviders: MutableList<BodyWriterChooserProvider> = ArrayList()

    fun add(provider: BodyWriterChooserProvider) {
        localChooserProviders.add(provider)
    }

    val allChooserProviders: List<BodyWriterChooserProvider>
    get() = if (parent != null) localChooserProviders + parent.allChooserProviders
    else localChooserProviders

    override fun <B : Any> forClass(target: Class<B>): BodyWriterChooser<B> {
        val choosers = allChooserProviders.mapNotNull { it.forClass(target) }
        return if (choosers.isEmpty()) throw NoWriterFoundForType(target)
        else BodyWriterChoosers(choosers)
    }
}

