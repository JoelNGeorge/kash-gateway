package com.kash.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableHystrix
public class GatewayConfig {

	@Autowired
	AuthenticationFilter filter;

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("kash-users", r -> r.path("/users/**").filters(f -> f.filter(filter)).uri("lb://kash-users"))
				.route("kash-auth", r -> r.path("/auth/**").filters(f -> f.filter(filter)).uri("lb://kash-auth"))
				.route("kash-wallet", r -> r.path("/wallet/**").filters(f -> f.filter(filter)).uri("lb://kash-wallet"))
				.build();
	}

}
