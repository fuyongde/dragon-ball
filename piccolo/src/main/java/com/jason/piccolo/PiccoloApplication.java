package com.jason.piccolo;

import org.apache.activemq.command.ActiveMQDestination;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import javax.jms.Destination;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class PiccoloApplication {

  public static void main(String[] args) {
    SpringApplication.run(PiccoloApplication.class, args);
  }

  @Bean
  public Destination mailDestination() {
    return ActiveMQDestination
        .createDestination("mail.queue", ActiveMQDestination.QUEUE_TYPE);
  }
}
