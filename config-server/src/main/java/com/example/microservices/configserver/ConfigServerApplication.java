package com.example.microservices.configserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

	@Autowired
	Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}

	@Bean
	public String dummyBean() {
		System.out.println("keystorelocation: " + environment.getProperty("encrypt.key-store.location"));
		return "DUMMY";
	}
}
