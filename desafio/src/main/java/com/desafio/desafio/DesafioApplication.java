package com.desafio.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan("com.desafio")
@EnableSwagger2
public class DesafioApplication {


	public static void main(String[] args) {
		SpringApplication.run(DesafioApplication.class, args);
	}

}
