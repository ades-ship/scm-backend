package com.scm.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.scm.v1")
public class V1Application {

	public static void main(String[] args) {
		SpringApplication.run(V1Application.class, args);
	}
}
