package io.neyb.swak.handler.path

import io.github.neyb.shoulk.*
import io.github.neyb.shoulk.matcher.*
import io.neyb.swak.http.NoPathParamExtractor
import org.junit.jupiter.api.Test

internal class ExactRoutePathTest {
    @Test
    internal fun `extract always return emptymap`() {
        val exactRoutePath = ExactRoutePath("/aze")
        exactRoutePath.extractor.extractFrom("/aze") shouldEqual emptyMap()
    }

    @Test
    internal fun `extract on not matching fails`() {
        val exactRoutePath = ExactRoutePath("/foo")
        exactRoutePath.extractor.extractFrom("/bar") shouldEqual emptyMap()
    }
}