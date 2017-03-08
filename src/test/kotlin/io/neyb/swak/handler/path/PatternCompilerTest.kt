package io.neyb.swak.handler.path

import io.github.neyb.shoulk.*
import io.github.neyb.shoulk.matcher.*
import io.neyb.swak.handler.path.PatternCompiler.compile
import org.junit.jupiter.api.Test

internal class PatternCompilerTest {
    fun havePattern(expected: String) = match<Regex>("have pattern \"$expected\"") { it.pattern == expected }

    private fun test(prePath: String, path: String, matchAll: Boolean, expectedPattern: String, vararg expectedGroups: String) {
        val (regex, groups) = compile(prePath, path, matchAll)
        regex should havePattern(expectedPattern)
        groups shouldEqual expectedGroups.asList()
    }

    @Test
    internal fun `compile can extract one path group`() {
        test("", "/{group1}", false, "/([^/]*?)", "group1")
    }

    @Test
    internal fun `compile can extract one prepath group`() {
        test("/{group1}", "", false, "/[^/]*?")
    }

    @Test
    internal fun `matchAll add right characters`() {
        test("/a", "/b", true, "^/a/b$")
    }

    @Test
    internal fun `2 groups separated by slash`() {
        test("", "/{group1}/{group2}", false, "/([^/]*?)/([^/]*?)", "group1", "group2")
    }

    @Test
    internal fun `sticken group fails`() {
        { compile("", "{group1}{group2}", false) } shouldThrow IllegalArgumentException::class that hasMessage("sticken groups are not allowed")
    }

    @Test
    internal fun `exclude from class with double dot`() {
        test("", "/{group1:/}", false, "/([^/]*?)", "group1")
    }

    @Test
    internal fun `use next char as separator`() {
        test("", "/{group1}-{group2}x{group3:r}/", false, "/([^-]*?)-([^/]*?)", "group1", "group2")
    }
}