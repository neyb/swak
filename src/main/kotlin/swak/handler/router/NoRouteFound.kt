package swak.handler.router

internal class NoRouteFound(path: String) : Exception("no route found for path \"$path\"")