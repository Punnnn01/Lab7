package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("net.start.*")
@EnableJpaRepositories(basePackages ="net.start.repository")
@EntityScan("net.start.model")

public class StartAppplication {

	public static void main(String[] args) {
		SpringApplication.run(StartAppplication.class);
	}
}
