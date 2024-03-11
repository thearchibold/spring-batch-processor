package com.thearchibold.springbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringBatchApplication {



	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
	}

}
