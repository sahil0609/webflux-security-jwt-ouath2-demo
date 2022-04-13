package org.github.webflux.auth.multipleauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class MultipleauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultipleauthApplication.class, args);
	}

}
