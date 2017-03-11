package io.swak.handler.interceptor.after.writer

import io.swak.handler.cross.route.matcher.RequestMatcher
import io.swak.handler.interceptor.after.AfterInterceptor

interface ResponseContentwriter<B> : RequestMatcher<B>, AfterInterceptor<B>