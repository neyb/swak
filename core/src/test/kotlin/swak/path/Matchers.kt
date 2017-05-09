package swak.path

import io.github.neyb.shoulk.matcher.*
import swak.handler.path.RoutePath

internal fun accept(path: String) = match<RoutePath>("""accept "$path"""") { it.accept(path) }
internal fun reject(path: String) = match<RoutePath>("""reject "$path"""") { !it.accept(path) }
