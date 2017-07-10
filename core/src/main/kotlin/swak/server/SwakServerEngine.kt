package swak.server

interface SwakServerEngine {
    fun start(rootHandler: RootReqHandler)
    fun stop()
}