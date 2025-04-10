package com.example.gateway_client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayClientApplication {

	private final static Logger logger = LoggerFactory.getLogger(GatewayClientApplication.class);
	public static void main(String[] args) {
		logger.info("connected to gateway client application");
		SpringApplication.run(GatewayClientApplication.class, args);
	}

}
