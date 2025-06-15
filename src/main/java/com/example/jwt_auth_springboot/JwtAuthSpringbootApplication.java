package com.example.jwt_auth_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.jwt_auth_springboot.Repository")
public class JwtAuthSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthSpringbootApplication.class, args);
	}
}
