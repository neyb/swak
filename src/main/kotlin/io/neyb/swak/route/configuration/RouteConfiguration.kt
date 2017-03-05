package io.neyb.swak.route.configuration

import io.neyb.swak.route.SubRouteConfigurer

interface RouteConfiguration<Body, InnerBody> : (SubRouteConfigurer<Body, InnerBody>) -> Unit