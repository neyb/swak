package io.neyb.swak.handler.path

import io.github.neyb.shoulk.*
import io.github.neyb.shoulk.matcher.*
import org.junit.jupiter.api.Test

internal class RegexRoutePathTest {
    @Test
    internal fun `regex work without any group`() {
        val routePath = RegexRoutePath("/test/", true)
        routePath should accept("/test/")
        routePath.extractor.extractFrom("/test/") shouldEqual mapOf()
    }

    @Test
    internal fun `regex accept can match with a group`() {
        val routePath = RegexRoutePath("/test/{foo}", true)
        routePath should accept("/test/bar")
        routePath.extractor.extractFrom("/test/bar") shouldEqual mapOf("foo" to "bar")
    }

    @Test
    internal fun `can match empty string`() {
        val routePath = RegexRoutePath("/test/{foo}", true)
        routePath should accept("/test/")
        routePath.extractor.extractFrom("/test/") shouldEqual mapOf("foo" to "")
    }

    @Test
    internal fun `extracting a rejected path should fail`() {
        val routePath = RegexRoutePath("/foo/{test}", true)
        routePath should reject("/bar/value");
        { routePath.extractor.extractFrom("/bar/value") } shouldThrow
                IllegalArgumentException::class that match { it.message == "incompatible path" }
    }

    @Test
    internal fun `several groups`() {
        val routePath = RegexRoutePath("/{foo}/{bar}/{empty}/{notEmpty}/{emptyAgain}", true)
        routePath should reject("/")
        routePath.extractor.extractFrom("/foo/bar//notEmpty/") shouldEqual mapOf(
                "foo" to "foo",
                "bar" to "bar",
                "empty" to "",
                "notEmpty" to "notEmpty",
                "emptyAgain" to "")
    }
}