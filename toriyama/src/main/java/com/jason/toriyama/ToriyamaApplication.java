package com.jason.toriyama;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ToriyamaApplication {

  public static void main(String[] args) {
    SpringApplication.run(ToriyamaApplication.class, args);
  }
}
