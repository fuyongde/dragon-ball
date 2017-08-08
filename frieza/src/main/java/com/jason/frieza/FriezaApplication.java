package com.jason.frieza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class FriezaApplication {

  public static void main(String[] args) {
    SpringApplication.run(FriezaApplication.class, args);
  }
}
