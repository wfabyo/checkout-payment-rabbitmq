package com.develcode.checkout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class CheckoutApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckoutApplication.class, args);
	}

}
