package com.dcone.eth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyEthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyEthApplication.class, args);
	}
}
