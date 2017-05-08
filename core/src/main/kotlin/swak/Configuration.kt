package swak

data class Configuration @JvmOverloads constructor(
        val port: Int = 80
) {
    class Builder {
        var configuration: Configuration = Configuration()

        fun withport(port: Int) = this.apply {
            configuration = configuration.copy(port = port)
        }

        fun build() = configuration
    }
}