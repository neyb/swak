package io.neyb.utils

import io.github.neyb.shoulk.matcher.*
import io.neyb.swak.chain.route.path.RoutePath

fun accept(path: String) = match<RoutePath>("""accept "$path"""") { it.accept(path) }
fun reject(path: String) = match<RoutePath>("""reject "$path"""") { !it.accept(path) }
