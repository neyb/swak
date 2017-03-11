package swak.handler.interceptor.after.writer

import swak.handler.cross.route.matcher.RequestMatcher
import swak.handler.interceptor.after.AfterInterceptor

interface ResponseContentwriter<B> : RequestMatcher<B>, AfterInterceptor<B>