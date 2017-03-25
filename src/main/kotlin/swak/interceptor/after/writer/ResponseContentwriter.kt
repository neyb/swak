package swak.interceptor.after.writer

import swak.interceptor.after.ResponseUpdater
import swak.matcher.RequestMatcher

interface ResponseContentwriter<B> : RequestMatcher<B>, ResponseUpdater<B>