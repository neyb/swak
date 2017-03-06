package io.neyb.swak.handler.cross

import io.neyb.swak.handler.cross.route.Route

class SeveralRouteFound(path: String, routes: List<Route<*>>) : Exception("""several route found for path "$path":
${routes.joinToString(separator = "\n") { "* $it" }}""")