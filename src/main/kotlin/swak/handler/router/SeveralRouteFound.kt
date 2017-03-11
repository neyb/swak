package swak.handler.router

import swak.handler.router.route.Route

internal class SeveralRouteFound(path: String, routes: List<Route>) : Exception("""several route found for path "$path":
${routes.joinToString(separator = "\n") { "* $it" }}""")