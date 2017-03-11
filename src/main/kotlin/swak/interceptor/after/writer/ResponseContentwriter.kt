package swak.interceptor.after.writer

import swak.interceptor.after.AfterInterceptor
import swak.matcher.RequestMatcher

interface ResponseContentwriter<B> : RequestMatcher<B>, AfterInterceptor<B>