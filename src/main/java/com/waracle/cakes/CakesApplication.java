package com.waracle.cakes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CakesApplication {

	private static final Logger log = LoggerFactory.getLogger(CakesApplication.class);

	public static void main(String[] args) {
		log.info("starting cakes application ...");
		SpringApplication.run(CakesApplication.class, args);
	}

}
