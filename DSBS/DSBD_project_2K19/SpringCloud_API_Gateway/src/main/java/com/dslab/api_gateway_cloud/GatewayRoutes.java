package com.dslab.api_gateway_cloud;

import org.springframework.cloud.gateway.route.RouteLocator;

import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutes {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route( r ->
                        r.path("/vms/**")

                                .filters(f ->
                                        f.rewritePath("/vms/(?<service>.*)", "/${service}")
                                )
                                .uri("http://videomanagementservice:8080/")
                )
                .route(r ->
                        r.path("/videofiles/**")
                                .uri("file:///var/videofiles/")
                )
                .build();
    }
}
