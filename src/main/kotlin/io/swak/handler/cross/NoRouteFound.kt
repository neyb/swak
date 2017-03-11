package io.swak.handler.cross

internal class NoRouteFound(path: String) : Exception("no route found for path \"$path\"")