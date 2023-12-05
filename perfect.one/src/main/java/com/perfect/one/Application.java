package com.perfect.one;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(final ApplicationContext ctx) {
		return args -> {
			System.out.println("Spring Boot has defined " + ctx.getBeanDefinitionNames().length + " beans.");
			System.out.println("-----------------------");
			System.out.println("Env variables:");
			System.getenv().forEach((key, value) -> System.out.println(key + "=" + value));
			System.out.println("-----------------------");
		};
	}

}
