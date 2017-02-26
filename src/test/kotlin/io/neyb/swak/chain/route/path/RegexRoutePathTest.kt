package io.neyb.swak.chain.route.path

import io.github.neyb.shoulk.*
import io.github.neyb.shoulk.matcher.*
import io.neyb.utils.*
import org.junit.jupiter.api.Test

internal class RegexRoutePathTest{
    @Test
    internal fun `regex work without any group`() {
        val routePath = RegexRoutePath("/test/")
        routePath should accept("/test/")
        routePath.extractPathParams("/test/") shouldEqual mapOf()
    }

    @Test
    internal fun `regex accept can match with a group`() {
        val routePath = RegexRoutePath("/test/{foo}")
        routePath should accept("/test/bar")
        routePath.extractPathParams("/test/bar") shouldEqual mapOf("foo" to "bar")
    }

    @Test
    internal fun `can match empty string`() {
        val routePath = RegexRoutePath("/test/{foo}")
        routePath should accept("/test/")
        routePath.extractPathParams("/test/") shouldEqual mapOf("foo" to "")
    }

    @Test
    internal fun `extracting a rejected path should fail`() {
        val routePath = RegexRoutePath("/foo/{test}")
        routePath should reject("/bar/value");
        { routePath.extractPathParams("/bar/value") } shouldThrow
                IllegalArgumentException::class that match { it.message == "incompatible path" }
    }

    @Test
    internal fun `several groups`() {
        val routePath = RegexRoutePath("/{foo}/{bar}/{empty}/{notEmpty}/{emptyAgain}")
        routePath should reject("/")
        routePath.extractPathParams("/foo/bar//notEmpty/") shouldEqual mapOf(
                "foo" to "foo",
                "bar" to "bar",
                "empty" to "",
                "notEmpty" to "notEmpty",
                "emptyAgain" to "")
    }
}