package io.neyb.swak.handler.cross

class NoRouteFound(path: String) : Exception("no route found for path \"$path\"")