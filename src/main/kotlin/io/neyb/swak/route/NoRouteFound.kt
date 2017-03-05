package io.neyb.swak.route

class NoRouteFound(path: String) : Exception("no route found for path \"$path\"")