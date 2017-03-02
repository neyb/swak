package io.neyb.swak.chain.route.interceptors.body.reader

import io.neyb.swak.chain.interceptor.before.BeforeInterceptor
import io.neyb.swak.chain.route.matcher.RequestMatcher

interface RequestContentReader<B> : RequestMatcher, BeforeInterceptor<String, B>