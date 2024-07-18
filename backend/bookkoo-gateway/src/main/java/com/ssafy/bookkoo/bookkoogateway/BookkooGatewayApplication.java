package com.ssafy.bookkoo.bookkoogateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BookkooGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookkooGatewayApplication.class, args);
	}

}
