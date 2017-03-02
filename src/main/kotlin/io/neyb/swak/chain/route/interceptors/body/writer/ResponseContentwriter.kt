package io.neyb.swak.chain.route.interceptors.body.writer

import io.neyb.swak.chain.interceptor.after.AfterInterceptor
import io.neyb.swak.chain.interceptor.before.BeforeInterceptor
import io.neyb.swak.chain.route.matcher.RequestMatcher

interface ResponseContentwriter<B> : RequestMatcher, AfterInterceptor<B>