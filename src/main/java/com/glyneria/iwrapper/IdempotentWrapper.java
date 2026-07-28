package com.glyneria.iwrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IdempotentWrapper {

	public static void main(String[] args) {
		SpringApplication.run(IdempotentWrapper.class, args);
	}

}
