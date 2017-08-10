package com.jason.bulma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BulmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BulmaApplication.class, args);
	}
}
