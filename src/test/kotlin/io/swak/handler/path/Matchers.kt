package io.swak.handler.path

import io.github.neyb.shoulk.matcher.*

internal fun accept(path: String) = match<RoutePath>("""accept "$path"""") { it.accept(path) }
internal fun reject(path: String) = match<RoutePath>("""reject "$path"""") { !it.accept(path) }
