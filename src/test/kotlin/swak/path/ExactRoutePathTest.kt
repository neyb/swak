package swak.path

import io.github.neyb.shoulk.*
import org.junit.jupiter.api.Test
import swak.handler.path.ExactRoutePath

internal class ExactRoutePathTest {
    @Test
    internal fun `extract always return emptymap`() {
        val exactRoutePath = ExactRoutePath("/aze", true)
        exactRoutePath.extractor.extractFrom("/aze") shouldEqual emptyMap()
    }

    @Test
    internal fun `extract on not matching fails`() {
        val exactRoutePath = ExactRoutePath("/foo", true)
        exactRoutePath.extractor.extractFrom("/bar") shouldEqual emptyMap()
    }
}