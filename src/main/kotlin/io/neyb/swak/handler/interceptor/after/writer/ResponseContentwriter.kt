package io.neyb.swak.handler.interceptor.after.writer

import io.neyb.swak.handler.cross.route.matcher.RequestMatcher
import io.neyb.swak.handler.interceptor.after.AfterInterceptor

interface ResponseContentwriter<B> : RequestMatcher<B>, AfterInterceptor<B>