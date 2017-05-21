package swak.path

import io.github.neyb.shoulk.*
import org.junit.Test
import swak.handler.path.ExactRoutePath

internal class ExactRoutePathTest {

    @Test fun `extract always return emptymap`() {
        val exactRoutePath = ExactRoutePath("/aze", true)
        exactRoutePath.extractor shouldBe null
    }

    @Test fun `extract on not matching fails`() {
        val exactRoutePath = ExactRoutePath("/foo", true)
        exactRoutePath.extractor shouldBe null
    }
}