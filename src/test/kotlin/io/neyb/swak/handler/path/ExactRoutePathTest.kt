package io.neyb.swak.handler.path

import io.github.neyb.shoulk.*
import io.github.neyb.shoulk.matcher.*
import org.junit.jupiter.api.Test

internal class ExactRoutePathTest {
    @Test
    internal fun `extract always return emptymap`() {
        val exactRoutePath = ExactRoutePath("/aze")
        exactRoutePath.extractPathParams("/aze") shouldEqual emptyMap()
    }

    @Test
    internal fun `extract on not matching fails`() {
        val exactRoutePath = ExactRoutePath("/foo");
        { exactRoutePath.extractPathParams("/bar") } shouldThrow
                IllegalArgumentException::class that match { it.message == "incompatible path" }
    }
}