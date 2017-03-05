package io.neyb.swak.route.interceptors.before.reader

import io.neyb.swak.route.interceptors.before.BeforeInterceptor
import io.neyb.swak.route.matcher.RequestMatcher

interface RequestContentReader<BodyBefore, BodyAfter> : RequestMatcher<BodyBefore>, BeforeInterceptor<BodyBefore, BodyAfter>