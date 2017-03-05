package io.neyb.swak.route.interceptors.after.writer

import io.neyb.swak.route.interceptors.after.AfterInterceptor
import io.neyb.swak.route.matcher.RequestMatcher

interface ResponseContentwriter<B> : RequestMatcher<B>, AfterInterceptor<B>