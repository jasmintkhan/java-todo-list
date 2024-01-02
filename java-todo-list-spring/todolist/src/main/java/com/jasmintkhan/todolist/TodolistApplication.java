package com.jasmintkhan.todolist;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodolistApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

	@Bean
    CommandLineRunner init() {
        return args -> {
            System.out.println("The application has started successfully!");
            // You can add initialization code here if needed
        };
    }

}
