package com.davipedro.desafioanotaai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class DesafioAnotaAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioAnotaAiApplication.class, args);
	}

}
