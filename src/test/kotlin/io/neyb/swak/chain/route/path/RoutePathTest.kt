package io.neyb.swak.chain.route.path

import io.github.neyb.shoulk.*
import io.github.neyb.shoulk.matcher.*
import org.junit.jupiter.api.Test

class RoutePathTest {
    @Test
    internal fun `of without {} returns an exactRoutePath`() {
        RoutePath.of("/test") should match("is a ExactRoutePath") { it is ExactRoutePath }
    }

    @Test
    internal fun `of with {} returns a patternRoutePath`() {
        RoutePath.of("/test/{}") should match("is a RegexRoutePath") { it is RegexRoutePath }
    }
}