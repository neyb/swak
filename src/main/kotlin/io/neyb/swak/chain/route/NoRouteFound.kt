package io.neyb.swak.chain.route

class NoRouteFound(path: String) : Exception("no route found for path \"$path\"")