package io.neyb.swak.chain.route.path

class ExactRoutePath(private val path:String) : RoutePath{
    override fun accept(requestPath: String) = requestPath == path

    override fun extractPathParams(requestPath: String): Map<String, String> =
            if (accept(requestPath)) emptyMap()
            else throw IllegalArgumentException("incompatible path")
}