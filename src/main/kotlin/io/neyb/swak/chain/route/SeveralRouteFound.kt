package io.neyb.swak.chain.route

class SeveralRouteFound(path: String, routes: List<Route<*>>) : Exception("""several route found for path "$path":
${routes.joinToString(separator = "\n") { "* $it" }}""")