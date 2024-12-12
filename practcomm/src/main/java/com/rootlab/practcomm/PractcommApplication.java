package com.rootlab.practcomm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PractcommApplication {

	public static void main(String[] args) {
		SpringApplication.run(PractcommApplication.class, args);
	}

}
