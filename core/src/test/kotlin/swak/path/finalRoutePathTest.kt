package swak.path

import io.github.neyb.shoulk.*
import io.github.neyb.shoulk.matcher.*
import org.junit.Test
import swak.handler.path.*

class finalRoutePathTest {
    @Test
    internal fun `of without {} returns an exactRoutePath`() {
        RoutePath.of("/test", true) should match("is a ExactRoutePath") { it is ExactRoutePath }
    }

    @Test
    internal fun `of with {} returns a patternRoutePath`() {
        RoutePath.of("/test/{}", true) should match("is a RegexRoutePath") { it is RegexRoutePath }
    }
}