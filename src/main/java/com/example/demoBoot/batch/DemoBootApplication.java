package com.example.demoBoot.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages ="com.example.demoBoot.batch")
public class DemoBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoBootApplication.class, args);
	}

}
