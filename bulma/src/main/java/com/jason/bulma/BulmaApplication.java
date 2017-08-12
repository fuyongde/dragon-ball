package com.jason.bulma;

import org.apache.activemq.command.ActiveMQDestination;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Queue;

@EnableEurekaClient
@SpringBootApplication
public class BulmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BulmaApplication.class, args);
	}
}
