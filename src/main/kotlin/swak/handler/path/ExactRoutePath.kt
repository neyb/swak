package swak.handler.path

internal class ExactRoutePath(private val path: String, private val full: Boolean) : RoutePath {
    override fun accept(requestPath: String) =
            if (full) requestPath == path
            else requestPath.startsWith(path)

    override val extractor = null
    override fun toString() = path
}