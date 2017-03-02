package io.neyb.swak.chain.configuration

import io.neyb.swak.chain.ChainConfigurer
import io.neyb.swak.chain.contentReader.StringContentReader
import io.neyb.swak.chain.interceptor.errorHandler.NoRouteInterceptor
import io.neyb.swak.chain.interceptor.errorHandler.SeveralRouteInterceptor

object DefaultChainConfiguration : ChainConfiguration {
    override fun invoke(chainConfigurer: ChainConfigurer) {
        chainConfigurer.apply {
            chain.errorHandlers.add(NoRouteInterceptor)
            chain.errorHandlers.add(SeveralRouteInterceptor)
            addContentReaderProvider(StringContentReader)
        }
    }
}