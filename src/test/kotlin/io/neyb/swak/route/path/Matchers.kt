package io.neyb.swak.route.path

import io.github.neyb.shoulk.matcher.*

fun accept(path: String) = match<RoutePath>("""accept "$path"""") { it.accept(path) }
fun reject(path: String) = match<RoutePath>("""reject "$path"""") { !it.accept(path) }
