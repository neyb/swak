package swak.interceptor.after.writer

import swak.matcher.RequestMatcher
import swak.interceptor.after.AfterInterceptor

interface ResponseContentwriter<B> : RequestMatcher<B>, AfterInterceptor<B>