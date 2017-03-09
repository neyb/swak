package io.neyb.swak.handler.path

import io.github.neyb.shoulk.*
import io.github.neyb.shoulk.matcher.*
import io.neyb.swak.handler.path.PathPatternCompiler.compile
import org.junit.jupiter.api.Test

internal class PathPatternCompilerTest {
    fun havePattern(expected: String) = match<Regex>("have pattern \"$expected\"") { it.pattern == expected }

    private fun test(path: String, expectedPattern: String, vararg expectedGroups: String) {
        val (regex, groups) = compile(path)
        regex should havePattern(expectedPattern)
        groups shouldEqual expectedGroups.asList()
    }

    @Test
    internal fun `compile can extract one path group`() {
        test("/{group1}", "^/(.*?)", "group1")
    }

    @Test
    internal fun `matchAll add right characters`() {
        test("/a/b", "^/a/b")
    }

    @Test
    internal fun `sticken group fails`() {
        { compile("{group1}{group2}") } shouldThrow IllegalPattern::class that
                hasMessage("\"{group1}{group2}\" is not a valid pattern: sticken groups are not allowed")
    }

    @Test
    internal fun `specifying a regex for a group`() {
        test("{g1:[0-9]+}", "^([0-9]+)", "g1")
    }

    @Test
    internal fun `illegal group regex should fail`() {
        { compile("{grp:**}") } shouldThrow IllegalPattern::class that
                hasMessage(match("start with right message") { it.startsWith("\"{grp:**}\" is not a valid pattern") })
    }

    @Test
    internal fun `2 groups separated by slash`() {
        test("/{grp1}/{grp2}", "^/([^/]*)/(.*?)", "grp1", "grp2")
    }

    @Test
    internal fun `2 groups separated by slash not optimized`() {
        test("/{group1:}/{group2:}", "^/(.*?)/(.*?)", "group1", "group2")
    }

    @Test
    internal fun `use next char as separator`() {
        test("/{group1}-{group2:}x{group3}/", "^/([^-]*)-(.*?)x([^/]*)/", "group1", "group2", "group3")
    }
}